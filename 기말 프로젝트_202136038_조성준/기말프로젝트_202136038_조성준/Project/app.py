import tkinter as tk
from tkinter import scrolledtext, messagebox, font
import pandas as pd
import networkx as nx
import json
import threading
from PIL import Image, ImageTk
import io
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
import os


# Matplotlib 한글 폰트 설정
def setup_matplotlib_font():
    base_dir = os.path.dirname(os.path.abspath(__file__))
    font_path = os.path.join(base_dir, 'assets', 'NanumGothic.ttf')

    if os.path.exists(font_path):
        fm.fontManager.addfont(font_path)
        font_name = fm.FontProperties(fname=font_path).get_name()
        plt.rcParams['font.family'] = font_name
        plt.rcParams['font.sans-serif'] = [font_name]
        print(f"[INFO] Matplotlib 한글 폰트: '{font_name}' 등록 및 설정 완료")
    else:
        print(f"[WARN] 폰트 파일을 찾을 수 없음: {font_path}")
        plt.rcParams['font.family'] = 'Malgun Gothic'
    plt.rcParams['axes.unicode_minus'] = False

# --- 데이터 로딩 ---
print("데이터 로딩 중...")
try:
    links_df = pd.read_csv('clean_links.csv')
    with open('redirect_map.json', 'r', encoding='utf-8') as f:
        redirect_map = json.load(f)

    # 알고리즘: 그래프 생성 - 엣지 리스트로부터 그래프 객체를 생성
    # 자료구조: NetworkX 방향 그래프 (DiGraph)
    G = nx.from_pandas_edgelist(links_df, 'source', 'destination', create_using=nx.DiGraph())
    print("그래프 생성 완료!")
except FileNotFoundError:
    root = tk.Tk()
    root.withdraw()
    messagebox.showerror("파일 오류", "데이터 파일(clean_links.csv 또는 redirect_map.json)을 찾을 수 없습니다.")
    exit()


class NamuwikiPathfinderGUI(tk.Tk):
    def __init__(self, graph, redirect_map):
        super().__init__()
        ###GUI
        self.graph = graph
        self.redirect_map = redirect_map

        self.title("나무위키 파도타기")
        self.geometry("1920x1080")

        main_frame = tk.Frame(self, padx=20, pady=20)
        main_frame.pack(fill=tk.BOTH, expand=True)

        tk.Label(main_frame, text="나무위키 파도타기🌊", font=("Malgun Gothic", 20, "bold")).pack(pady=(0, 10))

        input_frame = tk.Frame(main_frame)
        input_frame.pack(fill=tk.X, pady=10)

        self.start_entry = tk.Entry(input_frame, font=("Malgun Gothic", 11))
        self.start_entry.pack(side=tk.LEFT, expand=True, fill=tk.X, ipady=5, padx=5)

        tk.Label(input_frame, text="→", font=("Malgun Gothic", 11)).pack(side=tk.LEFT)

        self.end_entry = tk.Entry(input_frame, font=("Malgun Gothic", 11))
        self.end_entry.pack(side=tk.LEFT, expand=True, fill=tk.X, ipady=5, padx=5)

        self.search_button = tk.Button(main_frame, text="경로 탐색", font=("Malgun Gothic", 12, "bold"),
                                       command=self.search_path_threaded, pady=8)
        self.search_button.pack(pady=10, fill=tk.X)

        self.result_text = scrolledtext.ScrolledText(main_frame, height=5, state='disabled', font=("Malgun Gothic", 10))
        self.result_text.pack(pady=5, fill=tk.X, expand=False)
        self.image_label = tk.Label(main_frame)
        self.image_label.pack(pady=10, fill=tk.BOTH, expand=True)
        ###메인GUI끝
    def get_actual_node(self, node):
        return self.redirect_map.get(node, node)

    def search_path_threaded(self):
        start_node = self.start_entry.get()
        end_node = self.end_entry.get()
        if not start_node or not end_node:
            messagebox.showwarning("입력 오류", "시작과 도착 문서를 모두 입력해주세요.")
            return

        self.search_button.config(state='disabled', text="탐색 중...")
        self.update_result_text("경로를 탐색 중입니다. 잠시만 기다려주세요...")
        self.image_label.config(image='')

        thread = threading.Thread(target=self.run_search_task, args=(start_node, end_node))
        thread.daemon = True
        thread.start()

    def run_search_task(self, start_node, end_node):
        result_message = ""
        path_image = None
        try:
            start = self.get_actual_node(start_node)
            end = self.get_actual_node(end_node)

            if start not in self.graph or end not in self.graph:
                result_message = f"오류: 시작 노드 '{start}' 또는 도착 노드 '{end}'가 그래프에 없습니다."
            else:
                # 알고리즘: 너비 우선 탐색 (BFS)
                # 자료구조: 큐(networkx 라이브러리의 최단 경로 탐색 함수 nx.shortest_path()가 내부적으로 사용하는 핵심 자료구조)
                path = nx.shortest_path(self.graph, source=start, target=end)
                result_message = f"경로를 찾았습니다!\n\n" + " -> ".join(path) + f"\n\n클릭 수: {len(path) - 1}회"
                # 경로를 찾은 경우, 시각화 이미지 생성
                path_image = self.create_path_image(path)

        except nx.NetworkXNoPath:
            result_message = "두 문서 간의 경로를 찾을 수 없습니다."
        except Exception as e:
            result_message = f"알 수 없는 오류가 발생했습니다: {e}"

        self.after(0, self.show_final_result, result_message, path_image)

    def create_path_image(self, path):
        nodes_to_draw = set(path)
        for node in path:
            successors = list(self.graph.successors(node))
            nodes_to_draw.update(successors[:10])

        subgraph = self.graph.subgraph(nodes_to_draw)

        plt.figure(figsize=(16, 9), dpi=120)
        pos = nx.spring_layout(subgraph, k=1.0, iterations=50, seed=42)
        # 기본 스타일
        nx.draw_networkx_nodes(subgraph, pos, node_color='#add8e6', node_size=800)
        nx.draw_networkx_edges(subgraph, pos, alpha=0.4, edge_color='gray', arrows=False)
        nx.draw_networkx_labels(subgraph, pos, font_size=9)

        # 경로 강조
        path_edges = list(zip(path, path[1:]))
        nx.draw_networkx_nodes(subgraph, pos, nodelist=path, node_color='tomato', node_size=1000)
        nx.draw_networkx_edges(subgraph, pos, edgelist=path_edges, edge_color='red', width=2.5, arrows=True,
                               arrowsize=20)

        plt.axis('off')

        buf = io.BytesIO()
        plt.savefig(buf, format='png', bbox_inches='tight')
        buf.seek(0)
        plt.close()

        img = Image.open(buf)
        return ImageTk.PhotoImage(img)

    def show_final_result(self, result_message, result_image):
        self.update_result_text(result_message)
        if result_image:
            self.image_label.config(image=result_image)
            self.image_label.image = result_image  # 참조 유지
        self.search_button.config(state='normal', text="경로 탐색")

    def update_result_text(self, message):
        self.result_text.config(state='normal')
        self.result_text.delete('1.0', tk.END)
        self.result_text.insert(tk.END, message)
        self.result_text.config(state='disabled')

if __name__ == "__main__":
    setup_matplotlib_font()
    app = NamuwikiPathfinderGUI(G, redirect_map)
    app.mainloop()