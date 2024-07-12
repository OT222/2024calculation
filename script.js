let coef = [];

function displayEquation() {
    let coef_x = parseFloat(document.getElementById('coef_x').value) || 0;
    let coef_x2 = parseFloat(document.getElementById('coef_x2').value) || 0;
    let coef_x3 = parseFloat(document.getElementById('coef_x3').value) || 0;
    let coef_x4 = parseFloat(document.getElementById('coef_x4').value) || 0;
    let coef_x5 = parseFloat(document.getElementById('coef_x5').value) || 0;
    let constant = parseFloat(document.getElementById('constant').value) || 0;

    coef = [constant, coef_x, coef_x2, coef_x3, coef_x4, coef_x5];

    let equation = `${coef_x5}x^5 + ${coef_x4}x^4 + ${coef_x3}x^3 + ${coef_x2}x^2 + ${coef_x}x + ${constant} = 0`;
    document.getElementById('equation-display').innerText = equation;
    console.log("Equation displayed: ", equation);
}

function f(x, coef) {
    return coef[5] * x ** 5 + coef[4] * x ** 4 + coef[3] * x ** 3 + coef[2] * x ** 2 + coef[1] * x + coef[0];
}

function drawFunction(coef) {
    const canvas = document.getElementById('graph-canvas');
    const ctx = canvas.getContext('2d');

    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.beginPath();
    ctx.moveTo(0, canvas.height / 2);
    ctx.lineTo(canvas.width, canvas.height / 2);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(canvas.width / 2, 0);
    ctx.lineTo(canvas.width / 2, canvas.height);
    ctx.stroke();

    ctx.beginPath();
    for (let x = -canvas.width / 2; x <= canvas.width / 2; x += 0.1) {
        let y = f(x / 50, coef) * 50;
        if (x === -canvas.width / 2) {
            ctx.moveTo(x + canvas.width / 2, -y + canvas.height / 2);
        } else {
            ctx.lineTo(x + canvas.width / 2, -y + canvas.height / 2);
        }
    }
    ctx.stroke();
    console.log("Function drawn with coefficients: ", coef);
}

document.getElementById('execute').addEventListener('click', () => {
    displayEquation();
    drawFunction(coef);
    console.log('Execute button clicked');
    displayEquation();
    console.log('Equation displayed');
    drawFunction(coef);
    console.log('Function drawn');
});

document.addEventListener('DOMContentLoaded', (event) => {
    console.log('DOM fully loaded and parsed');
});

document.getElementById('execute').addEventListener('click', () => {
    console.log('Execute button clicked');
});
