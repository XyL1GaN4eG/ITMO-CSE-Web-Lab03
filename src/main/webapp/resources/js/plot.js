const center = 210;
const rightEdge = 410;
const leftEdge = 10;
const bottomEdge = 410;
const topEdge = 10;
const max = 420;
const l = (bottomEdge - topEdge) / 6;
const mainColor = '#007BFF';
const pointsByR = {}
let xList = [];
let yList = [];
let rList = [];
let hitList = [];

function drawGraph(r) {
    const canvas = document.getElementById("graphic");
    const context = canvas.getContext('2d');
    context.clearRect(0, 0, max, max);
    context.strokeStyle = mainColor;
    context.fillStyle = mainColor;
    context.globalAlpha = 1;
    context.beginPath();
    drawArrow(context, leftEdge, center, rightEdge, center);
    drawArrow(context, center, bottomEdge, center, topEdge);
    context.globalAlpha = 0.25;

    //rectangle
    context.fillRect(center, center, -r * l, r * l / 2);

    // triangle
    context.beginPath();
    context.moveTo(center, center);
    context.lineTo(center + r / 2 * l, center);
    context.lineTo(center, center + r * l);
    context.closePath();
    context.fill();

    //quadrant
    context.beginPath();
    context.moveTo(center - r * l, center);
    context.arc(center, center, r * l, 0, -Math.PI / 2, true);
    context.lineTo(center, center);
    context.closePath();
    context.fill();

    context.globalAlpha = 1;
    context.font = '15px monospace'

    context.fillText('-R', center - r * l, center);
    context.fillText('R', center, center - r * l);
    context.fillText('-R/2', center - r * l / 2, center);
    context.fillText('R/2', center, center - r * l / 2);
    context.fillText('R/2', center + r * l / 2, center);
    context.fillText('R', center + r * l, center);
    context.fillText('-R/2', center, center + r * l / 2);
    context.fillText('-R', center, center + r * l);
    drawAllDots(r);
}

function drawDot(x, y, color) {
    const canvas = document.getElementById("graphic");
    const context = canvas.getContext('2d');
    context.fillStyle = color;
    context.globalAlpha = 1;
    context.beginPath();
    context.moveTo(x, y);
    context.arc(x, y, 4, 0, 2 * Math.PI);
    context.closePath();
    context.fill();
}

function drawAllDots(r) {
    for (let i = 0; i < xList.length; i++) {
        drawDot(center + (xList[i] / rList[i]) * r * l, center - (yList[i] / rList[i]) * r * l,
            hitList[i] ? '#0F0' : '#F00');
    }
}

function saveDots(x, y, r, hit) {
    xList = x;
    yList = y;
    rList = r;
    hitList = hit;
}

function offset(el) {
    var rect = el.getBoundingClientRect(),
        scrollLeft = window.pageXOffset || document.documentElement.scrollLeft,
        scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    return {top: rect.top + scrollTop, left: rect.left + scrollLeft}
}

function validateX(x) {
    if (x > 3) {
        x = 3;
    }
    if (x < -3) {
        x = -2.99999;
    }
    return x;
}

function validateY(y) {
    if (y > 3) {
        y = 3;
    }
    if (y < -5) {
        y = -5;
    }
    return y;
}

function provideInteractiveGraphics() {
    const canvas = document.getElementById("graphic");
    let isFirstEnter = false
    canvas.addEventListener("click", function (e) {
        console.log("looool")
        console.log("provideInteractiveGraphics")
        console.log(e.clientX, e.clientY)
        let x = (e.offsetX - center) / l;
        let y = -(e.offsetY - center) / l;
        let r = document.getElementById('form:r').value
        document.getElementById('graphForm:hiddenX').value = x;
        document.getElementById('graphForm:hiddenY').value = y;
        document.getElementById('graphForm:hiddenR').value = r;
        document.getElementById("graphForm:hiddenSubmitButton").click();
    });
}

function clearDots() {
    const canvas = document.getElementById("graphic");
    const context = canvas.getContext('2d');
    context.clearRect(0, 0, max, max);
    xList = [];
    yList = [];
    rList = [];
    hitList = [];
    let currentR = document.getElementById('form:r').value;

    if (currentR != null && currentR !== 0) {
        drawGraph(currentR);
    } else {
        drawGraph(1);
    }
}

function drawArrow(context, fromX, fromY, tox, toy) {
    const headLen = 10;
    const dx = tox - fromX;
    const dy = toy - fromY;
    const angle = Math.atan2(dy, dx);
    context.moveTo(fromX, fromY);
    context.lineTo(tox, toy);
    context.lineTo(tox - headLen * Math.cos(angle - Math.PI / 6), toy - headLen * Math.sin(angle - Math.PI / 6));
    context.moveTo(tox, toy);
    context.lineTo(tox - headLen * Math.cos(angle + Math.PI / 6), toy - headLen * Math.sin(angle + Math.PI / 6));
    context.stroke();
}

function addDot(x, y, r) {
    let hit =
        //rectangle
        ((x >= -r) && (x <= 0) && (y >= -r / 2) && (y <= 0)) ||
        //triangle
        ((x >= 0) && (x <= r / 2) && (y >= -r) && (y <= 0) && (x * 2 - r <= y)) ||
        //half-half-circle
        ((x * x + y * y <= r * r) && (x >= 0) && (y >= 0))
    xList.push(x);
    yList.push(y);
    rList.push(r);
    hitList.push(hit);
    let i = xList.length - 1
    drawDot(center + (xList[i] / rList[i]) * r * l, center - (yList[i] / rList[i]) * r * l,
        hitList[i] ? '#0F0' : '#F00');
}

function addRowToTable(rowData) {
    var table = document.getElementById('results-table');
    console.log(table)
    const row = document.createElement("tr");
    Object.values(rowData).forEach(cellData => {
        const cell = document.createElement("td");
        cell.textContent = cellData;
        row.appendChild(cell);
    });
    table.appendChild(row);
}