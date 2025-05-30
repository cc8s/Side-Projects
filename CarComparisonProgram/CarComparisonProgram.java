import java.util.Scanner;

public class CarComparisonProgram {

    // 자동차 정보 저장 변수
    String name;
    double fuelEfficiency;
    int horsepower;
    double engineSize;
    int torque;
    double emission;
    int seatingCapacity;
    double price;

    // 생성자
    CarComparisonProgram(String name, double fuelEfficiency, int horsepower, double engineSize, int torque, double emission, int seatingCapacity, double price) {
        this.name = name;
        this.fuelEfficiency = fuelEfficiency;
        this.horsepower = horsepower;
        this.engineSize = engineSize;
        this.torque = torque;
        this.emission = emission;
        this.seatingCapacity = seatingCapacity;
        this.price = price;
    }

    // 세부 정보 출력 메서드
    void showDetails() {
        System.out.println("이름: " + name);
        System.out.println("연비: " + fuelEfficiency + " km/L");
        System.out.println("출력: " + horsepower + " HP");
        System.out.println("엔진: " + engineSize + " L");
        System.out.println("토크: " + torque + " Nm");
        System.out.println("배기량: " + emission + " L");
        System.out.println("승차인원: " + seatingCapacity + " 명");
        System.out.println("가격: " + price + " 만원");
    }

    // 두 자동차 비교 메서드
    static void compareCars(CarComparisonProgram car1, CarComparisonProgram car2) {
        System.out.println("연비 차이: " + (car1.fuelEfficiency - car2.fuelEfficiency) + " km/L");
        System.out.println("출력 차이: " + (car1.horsepower - car2.horsepower) + " HP");
        System.out.println("엔진 차이: " + (car1.engineSize - car2.engineSize) + " L");
        System.out.println("토크 차이: " + (car1.torque - car2.torque) + " Nm");
        System.out.println("배기량 차이: " + (car1.emission - car2.emission) + " L");
        System.out.println("승차인원 차이: " + (car1.seatingCapacity - car2.seatingCapacity) + " 명");
        System.out.println("가격 차이: " + (car1.price - car2.price) + " 만원");
    }

    // 예상 견적 계산 메서드
    static void calculateEstimate(double price, double savedMoney, double loanInterest, int months) {
        double loanAmount = price - savedMoney;
        double totalPayment = loanAmount * (1 + loanInterest / 100);
        double monthlyPayment = totalPayment / months;

        System.out.println("총 대출 금액: " + loanAmount + " 만원");
        System.out.println("총 지불 금액: " + totalPayment + " 만원");
        System.out.println("월별 할부 금액: " + monthlyPayment + " 만원");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 자동차 목록 생성
        CarComparisonProgram[] sedanCars = {
                new CarComparisonProgram("세단1", 15.0, 150, 2.0, 250, 2.0, 5, 3000),
                new CarComparisonProgram("세단2", 12.0, 180, 2.5, 300, 2.5, 5, 3500),
                new CarComparisonProgram("세단3", 10.0, 200, 3.0, 350, 3.0, 5, 4000)
        };

        CarComparisonProgram[] suvCars = {
                new CarComparisonProgram("SUV1", 10.0, 200, 3.0, 400, 3.0, 7, 5000),
                new CarComparisonProgram("SUV2", 8.0, 220, 3.5, 450, 3.5, 7, 6000),
                new CarComparisonProgram("SUV3", 9.0, 240, 4.0, 500, 4.0, 7, 7000)
        };

        CarComparisonProgram[] sportsCars = {
                new CarComparisonProgram("스포츠카1", 5.0, 300, 3.0, 500, 3.0, 2, 8000),
                new CarComparisonProgram("스포츠카2", 4.0, 350, 3.5, 550, 3.5, 2, 9000),
                new CarComparisonProgram("스포츠카3", 3.0, 400, 4.0, 600, 4.0, 2, 10000)
        };

        String[] categories = {"세단", "SUV", "스포츠카"};

        while (true) {
            // 카테고리 출력
            printCategories(categories);

            System.out.print("카테고리 선택 (1-3): ");
            int categoryChoice = getValidInput(scanner, 1, 3);

            CarComparisonProgram[] selectedCategory;

            switch (categoryChoice) {
                case 1:
                    selectedCategory = sedanCars;
                    break;
                case 2:
                    selectedCategory = suvCars;
                    break;
                case 3:
                    selectedCategory = sportsCars;
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
                    continue;
            }

            printCarList(selectedCategory);

            // 자동차 선택
            System.out.print("첫 번째 자동차 선택 (0-2): ");
            int carIndex1 = getValidInput(scanner, 0, 2);
            System.out.print("두 번째 자동차 선택 (0-2): ");
            int carIndex2 = getValidInput(scanner, 0, 2);

            compareCars(selectedCategory[carIndex1], selectedCategory[carIndex2]);

            System.out.print("선택한 자동차로 예상 견적을 계산하시겠습니까? (Y/N): ");
            char estimateChoice = getYesOrNo(scanner);

            if (estimateChoice == 'Y') {
                System.out.print("모은 금액 (만원): ");
                double savedMoney = scanner.nextDouble();
                System.out.print("대출 금리 (%): ");
                double loanInterest = scanner.nextDouble();
                System.out.print("할부 기간 (12, 24, 36개월): ");
                int months = scanner.nextInt();

                calculateEstimate(selectedCategory[carIndex1].price, savedMoney, loanInterest, months);
            }

            System.out.print("프로그램을 종료하시겠습니까? (Y/N): ");
            char exitChoice = getYesOrNo(scanner);
            if (exitChoice == 'Y') {
                break;
            }
        }

        scanner.close();
    }

    private static void printCategories(String[] categories) {
        System.out.println("자동차 카테고리 선택:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ") " + categories[i]);
        }
    }

    private static void printCarList(CarComparisonProgram[] cars) {
        System.out.println("자동차 선택:");
        for (int i = 0; i < cars.length; i++) {
            System.out.println(i + ") " + cars[i].name);
        }
    }

    // 유효한 입력을 받을 때까지 반복하여 입력받음
    private static int getValidInput(Scanner scanner, int min, int max) {
        int input;
        while (true) {
            input = scanner.nextInt();
            if (input >= min && input <= max) {
                break;
            } else {
                System.out.println("잘못된 입력입니다. 다시 시도하세요 (" + min + " - " + max + "): ");
            }
        }
        return input;
    }

    // Y/N 입력 처리
    private static char getYesOrNo(Scanner scanner) {
        char input;
        while (true) {
            input = scanner.next().toUpperCase().charAt(0);
            if (input == 'Y' || input == 'N') {
                break;
            } else {
                System.out.println("잘못된 입력입니다. Y 또는 N을 입력하세요: ");
            }
        }
        return input;
    }
}
