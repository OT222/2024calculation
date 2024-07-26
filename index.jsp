<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>グラフ描画</title>
    <script>
  		var executeCount = 0; // 実行回数の初期値
        function updateGraph() {
        	executeCount++;
            // フォームの入力値を取得
            var coef_x = document.getElementsByName("coef_x")[0].value;
            var coef_x2 = document.getElementsByName("coef_x2")[0].value;
            var coef_x3 = document.getElementsByName("coef_x3")[0].value;
            var coef_x4 = document.getElementsByName("coef_x4")[0].value;
            var coef_x5 = document.getElementsByName("coef_x5")[0].value;
            var constant = document.getElementsByName("constant")[0].value;
            var bisection_point_a = document.getElementsByName("bisection_point_a")[0].value;
            var bisection_point_b = document.getElementsByName("bisection_point_b")[0].value;
            var newton_initial = document.getElementsByName("newton_initial")[0].value;
            var tolerance = document.getElementsByName("tolerance")[0].value;

            console.log("実行回数: " + executeCount);

            // グラフの画像URLを更新
            var img = document.getElementById("graphImage");
            img.src = "GraphServlet?coef_x=" + coef_x +
                      "&coef_x2=" + coef_x2 +
                      "&coef_x3=" + coef_x3 +
                      "&coef_x4=" + coef_x4 +
                      "&coef_x5=" + coef_x5 +
                      "&constant=" + constant +
                      "&bisection_point_a=" + bisection_point_a +
                      "&bisection_point_b=" + bisection_point_b +
                      "&newton_initial=" + newton_initial +
                      "&tolerance=" + tolerance;
        }

        function resetCount() {
            // 実行回数をリセットしてグラフも初期化
            executeCount = 0;
            document.getElementById('count').textContent = executeCount;
            document.getElementById('graph').src = '';
            console.log("カウントをリセットしました。");
        }
        
    </script>
</head>
<body>
    <form onsubmit="updateGraph(); return false;">
        xの係数: <input type="text" name="coef_x"><br>
        x^2の係数: <input type="text" name="coef_x2"><br>
        x^3の係数: <input type="text" name="coef_x3"><br>
        x^4の係数: <input type="text" name="coef_x4"><br>
        x^5の係数: <input type="text" name="coef_x5"><br>
        定数: <input type="text" name="constant"><br>
        二分法の初期値a: <input type="text" name="bisection_point_a"><br>
        二分法の初期値b: <input type="text" name="bisection_point_b"><br>
        ニュートン法の初期値: <input type="text" name="newton_initial"><br>
        許容誤差: <input type="text" name="tolerance"><br>
        <input type="submit" value="実行">
        <button type="button" onclick="resetCount();">リセット</button>
        <input type="submit" value="計算過程">
    </form>
    <h3>グラフ：</h3>
    <img id="graphImage" src="GraphServlet" alt="グラフ" width="1200" height="1200">
</body>
</html>
