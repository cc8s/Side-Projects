//컴퓨터공학과 202136038 조성준
import java.util.Scanner;

public class CarComparisonProgram {

    // 자동차 데이터 초기화
    static Object[][][] cars = {
            {
                    {"BMW M2", 8.5, 460, 2993, 2, "I6", 4.1, 9000.0},
                    {"포르쉐 911", 6.8, 662, 3745, 2, "F6", 2.8, 32000.0},
                    {"포드 머스탱", 7.2, 493, 5038, 2, "V8", 3.6, 8600.0}
            },
            {
                    {"벤츠 E200", 12.4, 204, 1999, 5, "I4", 7.5, 7000.0},
                    {"볼보 S90", 11.7, 250, 1969, 5, "I4", 7.2, 7300.0},
                    {"아우디 A4", 16.9, 163, 1968, 5, "I4", 8.2, 6700.0}
            },
            {
                    {"벤츠 G클래스", 10, 387, 2989, 7, "I6", 5.8, 18000.0},
                    {"랜드로버 디펜더110", 9.8, 2497, 2997, 7, "I6", 8.3, 13000.0},
                    {"BMW X7", 7.8, 381, 2998, 7, "I6", 5.8, 15000.0}
            }
    };

    //메인메뉴
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("###메인 메뉴###\n 1. 상세제원\n 2. 성능비교\n 0. 종료");
            int menuChoice = scanner.nextInt();

            if (menuChoice == 0) break;

            switch (menuChoice) {
                case 1:
                    showCarDetails(scanner);
                    break;
                case 2:
                    compareCarPerformance(scanner);
                    break;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
        scanner.close();
    }

    // 상세 제원 보기(메뉴1)
    private static void showCarDetails(Scanner scanner) {
        int category = chooseCategory(scanner);
        int carIndex = chooseCar(scanner, category);

        printCarDetails(category, carIndex);
        //상세견적으로 넘어갈지 말지 선택
        System.out.print("견적을 내볼까요? (Y/N): ");
        String response = scanner.next();
        if (response.equals("Y")) {
            showEstimateMenu(scanner, category, carIndex);
        }
    }

    // 성능 비교(메뉴2)
    private static void compareCarPerformance(Scanner scanner) {
        System.out.println("자동차 선택:");
        int category1 = chooseCategory(scanner);
        int carIndex1 = chooseCar(scanner, category1);

        System.out.println("비교할 자동차 선택:");
        int category2 = chooseCategory(scanner);
        int carIndex2 = chooseCar(scanner, category2);

        compareCars(category1, carIndex1, category2, carIndex2);

        System.out.print("견적을 내볼까요? (Y/N): ");
        String response = scanner.next();
        if (response.equals("Y")) {
            showEstimateMenu(scanner, category1, carIndex1);
        }
    }

    // 카테고리 선택
    private static int chooseCategory(Scanner scanner) {
        System.out.println("카테고리를 선택하세요\n 0. 스포츠카\n 1. 세단\n 2. SUV");
        return scanner.nextInt();
    }

    // 자동차 선택
    private static int chooseCar(Scanner scanner, int category) {
        System.out.println("##선택 가능한 차량##");
        for (int i = 0; i < cars[category].length; i++) {
            System.out.println(i + ". " + cars[category][i][0]);
        }
        System.out.print("원하는 차량 번호를 입력하세요(0,1,2):\n ");
        return scanner.nextInt();
    }

    // 자동차 상세 제원 출력
    private static void printCarDetails(int category, int carIndex) {
        System.out.println("차량명: " + cars[category][carIndex][0]);
        System.out.println("연비: " + cars[category][carIndex][1] + " km/L");
        System.out.println("출력: " + cars[category][carIndex][2] + " hp");
        System.out.println("배기량: " + cars[category][carIndex][3] + " cc");
        System.out.println("탑승 인원: " + cars[category][carIndex][4]);
        System.out.println("엔진 형식: " + cars[category][carIndex][5]);
        System.out.println("0-100km 도달 시간: " + cars[category][carIndex][6] + " 초");
        System.out.println("가격(만원): ₩" + cars[category][carIndex][7] + "\n");
    }

    // 성능 비교
    private static void compareCars(int category1, int carIndex1, int category2, int carIndex2) {
        System.out.println("성능 비교 결과\n");
        System.out.println("출력 차이: " + ((int) cars[category1][carIndex1][2] - (int) cars[category2][carIndex2][2]) + " hp");
        System.out.println("연비 차이: " + ((double) cars[category1][carIndex1][1] - (double) cars[category2][carIndex2][1]) + " km/L");
        System.out.println("배기량 차이: " + ((int) cars[category1][carIndex1][3] - (int) cars[category2][carIndex2][3]) + " cc");
        System.out.println("0-100km 도달 시간 차이: " + ((double) cars[category1][carIndex1][6] - (double) cars[category2][carIndex2][6]) + " 초");
    }

    // 견적 메뉴
    private static void showEstimateMenu(Scanner scanner, int category, int carIndex) {
        double price = (double) cars[category][carIndex][7];
        System.out.println("\n선택 차량: " + cars[category][carIndex][0]);
        System.out.println("차량 가격(만원): ₩" + price);
        System.out.print("모은 돈을 입력하세요: ");
        double savedMoney = scanner.nextDouble();
        double loanAmount = price - savedMoney;

        System.out.print("대출 금리를 입력하세요 (%): ");
        double interestRate = scanner.nextDouble() / 100;

        System.out.print("할부 개월 선택 (12, 24, 36): ");
        int installmentMonths = scanner.nextInt();

        double monthlyPayment = calculateMonthlyPayment(loanAmount, interestRate, installmentMonths);
        double totalInterest = monthlyPayment * installmentMonths - loanAmount;

        System.out.println("\n상세 견적서(단위-만원)\n");
        System.out.println("차량명: " + cars[category][carIndex][0]);
        System.out.println("차량 가격: ₩" + price);
        System.out.println("모은 돈: ₩" + savedMoney);
        System.out.println("대출 필요 금액: ₩" + loanAmount);
        System.out.println("대출 금리: " + (interestRate * 100) + "%");
        System.out.println("할부 개월: " + installmentMonths + "개월");
        System.out.println("월 납입금: ₩" + monthlyPayment);
        System.out.println("총 이자: ₩" + totalInterest + "\n");
    }

    // 월 납입금 계산
    private static double calculateMonthlyPayment(double loanAmount, double interestRate, int months) {
        double monthlyRate = interestRate / 12;
        double factor = 1.0;

        for (int i = 0; i < months; i++) {
            factor *= (1 + monthlyRate);
        }

        return (loanAmount * monthlyRate) / (1 - (1 / factor));
    }
}
