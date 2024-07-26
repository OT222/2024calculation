package houteishiki;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GraphServlet")
public class GraphServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        // パラメータの取得
        String coef_x = request.getParameter("coef_x");
        String coef_x2 = request.getParameter("coef_x2");
        String coef_x3 = request.getParameter("coef_x3");
        String coef_x4 = request.getParameter("coef_x4");
        String coef_x5 = request.getParameter("coef_x5");
        String constant = request.getParameter("constant");
        String bisection_point_a = request.getParameter("bisection_point_a");
        String bisection_point_b = request.getParameter("bisection_point_b");
        String newton_initial = request.getParameter("newton_initial");

        // グラフの描画
        int width = 1200; // グラフの幅
        int height = 1200; // グラフの高さ
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // 背景を白に設定
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // x軸とy軸の描画
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, height / 2, width, height / 2); // x軸
        g2d.drawLine(width / 2, 0, width / 2, height); // y軸

        if (coef_x != null && coef_x2 != null && coef_x3 != null && coef_x4 != null && coef_x5 != null && constant != null) {
            // 与えられた関数のグラフを描画
            double[] coefficients = {
                Double.parseDouble(coef_x5),
                Double.parseDouble(coef_x4),
                Double.parseDouble(coef_x3),
                Double.parseDouble(coef_x2),
                Double.parseDouble(coef_x),
                Double.parseDouble(constant)
            };
            drawGraph(g2d, coefficients, width, height);

            // グラフ上の点を描画
            if (bisection_point_a != null && !bisection_point_a.isEmpty()) {
                double point_a = Double.parseDouble(bisection_point_a);
                double point_b = Double.parseDouble(bisection_point_b);
                int xc = (int) (width / 2 + ((point_a + point_b)/2) * 20); // x座標をスケーリング
                int xa = (int) (width / 2 + (point_a * 20)); // x座標をスケーリング
                int xb = (int) (width / 2 + (point_b * 20)); // x座標をスケーリング
                double yValc = evaluatePolynomial(coefficients, (point_a + point_b)/2);
                double yVala = evaluatePolynomial(coefficients, point_a);
                double yValb = evaluatePolynomial(coefficients, point_b);
                int yc = (int) (height / 2 - yValc * 20); // y座標をスケーリング
                int ya = (int) (height / 2 - yVala * 20); // y座標をスケーリング
                int yb = (int) (height / 2 - yValb * 20); // y座標をスケーリング
                g2d.setColor(Color.BLACK);
                g2d.fillOval(xc - 5, yc - 5, 10, 10); // 中心点に赤い点を描画
                g2d.setColor(Color.PINK);
                g2d.fillOval(xa - 5, ya - 5, 10, 10); // 中心点に赤い点を描画
                g2d.setColor(Color.PINK);
                g2d.fillOval(xb - 5, yb - 5, 10, 10); // 中心点に赤い点を描画
            }

            // ニュートン法の初期値における接線を描画
            if (newton_initial != null && !newton_initial.isEmpty()) {
                double initial = Double.parseDouble(newton_initial);
                drawTangentLine(g2d, coefficients, initial, width, height);
            }
        }

        g2d.dispose();

        response.setContentType("image/png");
        ImageIO.write(bufferedImage, "png", response.getOutputStream());
    }

	public void drawGraph(Graphics2D g2d, double[] coefficients, int width, int height) {
        g2d.setColor(Color.BLUE);
        for (int i = -width / 2; i < width / 2; i++) {
            double x1 = i / 20.0;
            double x2 = (i + 1) / 20.0;
            double y1 = evaluatePolynomial(coefficients, x1);
            double y2 = evaluatePolynomial(coefficients, x2);

            int screenX1 = (int) (width / 2 + x1 * 20);
            int screenY1 = (int) (height / 2 - y1 * 20);
            int screenX2 = (int) (width / 2 + x2 * 20);
            int screenY2 = (int) (height / 2 - y2 * 20);

            g2d.drawLine(screenX1, screenY1, screenX2, screenY2);
        }
    }

	public void drawTangentLine(Graphics2D g2d, double[] coefficients, double x0, int width, int height) {
        // 関数f(x)の値とf'(x)の値を計算
        double fx = evaluatePolynomial(coefficients, x0);
        double dfx = evaluatePolynomialDerivative(coefficients, x0);

        // 接線の方程式 y = f(x0) + f'(x0)(x - x0)
        // グラフの描画範囲に合わせて座標をスケーリング
        g2d.setColor(Color.GREEN);
        // 初期値の位置に赤い点を描画
        int x = (int) (width / 2 + x0 * 20);
        int y = (int) (height / 2 - fx * 20);
        g2d.setColor(Color.RED);
        g2d.fillOval(x - 5, y - 5, 10, 10);
        
        // 接線とx軸との交点のx座標を計算して使用
        double tangentXIntercept = calculateTangentXIntercept(coefficients, x0);
        // ここで、tangentXInterceptを利用したい処理を追加することができます
       // System.out.println("接線とx軸との交点のx座標: " + tangentXIntercept);
        
        int xx = (int) (width / 2 + (tangentXIntercept * 20));
        int yy = (int) (height / 2);
        g2d.setColor(Color.ORANGE);
        g2d.fillOval(xx - 5, yy - 5, 10, 10);

        // 接線の描画
        g2d.setColor(Color.GREEN);
        double dx = 20; // x軸方向の移動距離（仮の値、必要に応じて調整）
        int x1 = (int) (x - dx * 20);
        int y1 = (int) (y + dfx * dx * 20); // dxの分だけx0での傾き分移動
        int x2 = (int) (x + dx * 20);
        int y2 = (int) (y - dfx * dx * 20); // dxの分だけx0での傾き分移動
        g2d.drawLine(x1, y1, x2, y2);
    }

	public double evaluatePolynomial(double[] coefficients, double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
        }
        return result;
    }

	public double evaluatePolynomialDerivative(double[] coefficients, double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length - 1; i++) {
            result += coefficients[i] * (coefficients.length - 1 - i) * Math.pow(x, coefficients.length - 2 - i);
        }
        return result;
    }

	public double calculateTangentXIntercept(double[] coefficients, double x0) {
        double fx = evaluatePolynomial(coefficients, x0);
        double dfx = evaluatePolynomialDerivative(coefficients, x0);
        return x0 - (fx / dfx);
    }
}
