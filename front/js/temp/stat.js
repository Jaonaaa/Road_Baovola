import { setUpNavbar } from "../components/Navbar.js";
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
  let chart = document.createElement("canvas");
  let types = datas.map((d) => d.typeEntreprise.name);

  let filtredData = [];

  datas.forEach((d) => {
    let total = 0;
    d.ventes.forEach((v) => {
      total += v.quantity;
    });
    filtredData.push(total);
  });

  new Chart(chart, {
    type: "doughnut",
    data: {
      labels: types,
      datasets: [
        {
          label: "Quantité",
          data: filtredData,
          borderWidth: 1,
        },
      ],
    },
    options: {
      scales: {
        y: {
          beginAtZero: true,
        },
      },
    },
  });

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
    // console.log(datas);
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
  let da = "";
  datas.forEach((data) => {
    let total = 0;
    data.ventes.forEach((v) => {
      total += v.quantity;
    });
    da += `
        <tbody>
        <tr>
          <td>${data.typeEntreprise.name}</td>
          <td>${total}</td>
        </tr>
      </tbody>
        
        `;
  });
  let table = `
  <table id="table_result">
        <tbody>
          <tr>
            <th>Type</th>
            <th>Quantity</th>
          </tr>
        </tbody>
        ${da}
      </table>
  `;

  let list = document.querySelector(".list_tab");
  list.innerHTML += table;
}
