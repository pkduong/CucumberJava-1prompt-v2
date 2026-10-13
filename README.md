# [FPT] Thử thách 1 prompt - Version 2:

Có thêm 3 yếu tố: Tích hợp Allure report, Data-driven testing (login với nhiều user) và tích hợp CI/CD GitHub Actions

```
Hãy setup hoàn chỉnh project Java Selenium + Cucumber để test đăng nhập tại https://www.saucedemo.com, theo Maven và Page Object Model chuẩn.

Yêu cầu:

- Dùng Java 17+, Selenium, Cucumber và JUnit 5 với các phiên bản tương thích.
- Tạo cấu trúc Maven chuẩn, Cucumber runner, hooks quản lý WebDriver, Page Object, step definitions và feature đăng nhập thành công.
- Dùng Selenium Manager để tự tìm browser driver.
- Dùng **explicit wait** (`WebDriverWait`/`ExpectedConditions`) để chờ form hiển thị sau khi mở trang, chờ các trường nhập liệu và nút login sẵn sàng, rồi chờ trang sản phẩm sau khi đăng nhập. Không dựa vào `implicit wait` hoặc `Thread.sleep()`.
- Tích hợp Allure Report tương thích với Cucumber và JUnit Platform. Ghi kết quả test vào Allure results, cấu hình cách tạo report và ghi rõ các lệnh để chạy test và xem report.
- Chuyển kiểm thử đăng nhập sang data-driven testing bằng Cucumber `Scenario Outline` và `Examples` hoặc một cách tương đương phù hợp. Chạy lần lượt với các username `standard_user`, `locked_out_user`, `problem_user`, `performance_glitch_user`, `error_user`, `visual_user`; mật khẩu dùng chung cho tất cả là `secret_sauce`.
- Lưu base URL, danh sách username và mật khẩu dùng chung trong file cấu hình; không hardcode URL hay thông tin đăng nhập trong step definitions hoặc Page Objects.
- Assertion phải phản ánh đúng kết quả của từng user: `locked_out_user` phải bị từ chối và hiển thị thông báo lỗi phù hợp; các user còn lại phải đăng nhập thành công và thấy trang Products.
- Tích hợp GitHub Actions để chạy `mvn test -Dheadless=true` khi có pull request và khi push lên nhánh chính. Dùng Java 17, lưu lại Allure results/report thành artifact để có thể tải từ workflow run, và cấu hình workflow theo hướng bảo trì được.
- Chạy test tại local bằng `mvn test -Dheadless=true`, kiểm tra kết quả và report. Nếu có lỗi, hãy đọc log, xác định nguyên nhân, sửa rồi chạy lại đến khi pass; không báo thành công nếu chưa chạy được. Cuối cùng liệt kê các file đã tạo hoặc chỉnh sửa và cách xem Allure report.
```

# SauceDemo Selenium + Cucumber login tests

Maven-based browser tests using Java 17, Selenium, Cucumber, JUnit Platform, and Allure. Each configured account runs as a separate Scenario Outline example. Browser drivers are resolved automatically by Selenium Manager.

## Prerequisites

- JDK 17 or newer
- Maven 3.8.9 or newer
- Google Chrome installed

## Run the tests

Run headlessly (the same command used in GitHub Actions):

```shell
mvn test -Dheadless=true
```

Run with a visible browser:

```shell
mvn test -Dheadless=false
```

## Allure report

```shell
mvn allure:serve
```

The command generates a report from `target/allure-results`, starts a temporary local server, and opens the report in a browser. If it does not open automatically, use the URL printed in the Maven output. Stop the server with `Ctrl+C`.

The static report files are still available under `target/site/allure-maven-plugin/`; when you need to serve that already-generated folder, run this from the project root in PowerShell:

```powershell
py -m http.server 8000 --directory target/site/allure-maven-plugin
```

Then open `http://localhost:8000` in a browser and stop the server with `Ctrl+C`.

The GitHub Actions workflow uploads both the raw results and generated HTML report as an artifact named `allure-report-<run number>`.

## Test configuration

The base URL, ordered account list, shared password, and locked account are in `src/test/resources/test-data.properties`. The feature Examples select entries by zero-based index, keeping credentials out of step definitions and Page Objects.
