const center = 210;
const rightEdge = 410;
const leftEdge = 10;
const bottomEdge = 410;
const topEdge = 10;
const max = 420;
const l = (bottomEdge - topEdge) / 6;
const mainColor = '#007BFF';
let pointsByR = {}

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

function drawAllDots() {

    let r = Number(document.getElementById('form:r').value);

    if (!pointsByR[r]) {
        pointsByR[r] = [];
    }

    console.log("попытка нарисовать рки для следующего массива: ", pointsByR[r])
    console.log("r = ", r)
    pointsByR[r].forEach(point => {
        drawDot(center + (point.x / r) * r * l, center - (point.y / r) * r * l,
            point.hit ? '#0F0' : '#F00');
    })
}

function saveDots(x, y, r, hit) {
    for (let i = 0; i <= x.length - 1; i++) {
        let point = {
            x: x[i],
            y: y[i],
            hit: hit[i]
        };
        if (!pointsByR[r[i]]) {
            pointsByR[r[i]] = [];
        }
        pointsByR[r[i]].push(point)
    }
    console.log("полученные рки: ", pointsByR)
}

function offset(el) {
    const rect = el.getBoundingClientRect(),
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
    canvas.addEventListener("click", function (e) {
        let x = (e.offsetX - center) / l;
        let y = -(e.offsetY - center) / l;
        let r = document.getElementById('form:r').value;
        if (!pointsByR[r]) {
            pointsByR[r] = [];
        }
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
    pointsByR = null;
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

function addDot(data) {

    const x = data.x;
    const y = data.y;
    const r = data.r;
    const hit = data.hit;

    let point = {
        x: x,
        y: y,
        hit: hit
    };

    if (!pointsByR[r]) {
        pointsByR[r] = [];
    }

    pointsByR[r].push(point)
    let curR = document.getElementById('form:r').value;
    let pointsBCurrentR = pointsByR[curR]


    let i = pointsBCurrentR.size - 1;
    drawDot(center + (pointsBCurrentR.x[i] / pointsBCurrentR.r[i]) * r * l, center - (pointsBCurrentR.y[i] / pointsBCurrentR.y[i]) * r * l,
        pointsBCurrentR.hit[i] ? '#0F0' : '#F00');
}

function addRowToTable(rowData) {
    var table = document.getElementById('results-table');
    const row = document.createElement("tr");
    Object.values(rowData).forEach(cellData => {
        const cell = document.createElement("td");
        cell.textContent = cellData;
        row.appendChild(cell);
    });
    table.appendChild(row);
}