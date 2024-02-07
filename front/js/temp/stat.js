import { setUpNavbar } from "../components/Navbar.js";
import { createChart, createTable } from "../utils/MyChart.js";
import { get } from "../utils/fetchers.js";

setUpNavbar();
handleFilter();
getAll();

async function handleFilter() {
  let filter_box = document.getElementById("filter");
  let roadType = await get("road/type_quality");
  roadType.data.forEach((road) => {
    let filter = document.createElement("div");
    filter.classList.add("filter_item");
    filter.setAttribute("value", road.id);
    filter.innerHTML += `${road.type.name} | ${road.quality.name} | ${road.size.name} `;
    filter.addEventListener("click", async () => {
      let list = document.querySelector(".list_tab");
      list.innerHTML = "";
      getDataItem(road.id);
    });
    filter_box.appendChild(filter);
  });
}

function buildChart(datas) {
  let block = document.createElement("div");
  block.classList.add("chart_box");
  let types = datas.map((d) => d.typeEntreprise.name);

  let filtredData = [];

  datas.forEach((d) => {
    let total = 0;
    d.ventes.forEach((v) => {
      total += v.quantity;
    });
    filtredData.push(total);
  });

  let chart = createChart(types, filtredData, "doughnut", "Qt");

  let box = document.querySelector(".list_graph");
  if (box) {
    block.appendChild(chart);
    box.appendChild(block);
  }
}

function getAll() {
  let btn = document.querySelector(".all");
  btn.addEventListener("click", async () => {
    let datas = await get("client/stats");
    cleanTable();
    cleanChart();
    datas.data.forEach((dat) => {
      buildChart(dat);
      buildTable(dat);
    });
  });
}
function cleanChart() {
  let box = document.querySelector(".list_graph");
  box.innerHTML = "";
}
function cleanTable() {
  let list = document.querySelector(".list_tab");
  list.innerHTML = "";
}
async function getDataItem(idRoadtypeQuality) {
  let datas = await get("client/stat/" + idRoadtypeQuality);
  console.log(datas);
  cleanChart();
  buildChart(datas.data);
  buildTable(datas.data);
}

function buildTable(datas) {
  let rows = [];
  datas.forEach((data) => {
    let total = 0;
    data.ventes.forEach((v) => {
      total += v.quantity;
    });
    rows.push([data.typeEntreprise.name, total]);
  });

  let table = createTable(["Type", "Quantité"], rows);
  table.setAttribute("id", "table_result");

  let list = document.querySelector(".list_tab");
  list.innerHTML += table.outerHTML;
}
