export const createChart = (labels, datas, type = "doughnut", labelData = "Quantité") => {
  let chart = document.createElement("canvas");
  new Chart(chart, {
    type: type,
    data: {
      labels: labels,
      datasets: [
        {
          label: labelData,
          data: datas,
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
  return chart;
};

/**
 *
 * @param {[]} headers
 * @param {[][]} rows
 * @returns {HTMLElement}
 */
export const createTable = (headers, rows) => {
  let table = document.createElement("table");
  let text_html = "";
  text_html += `<tr> `;
  headers.forEach((head) => {
    text_html += `<th> ${head} </th>`;
  });
  text_html += `</tr> `;
  rows.forEach((row) => {
    text_html += `<tr> `;
    row.forEach((cell) => {
      text_html += `<td> ${cell} </td>`;
    });
    text_html += `</tr> `;
  });
  table.innerHTML = text_html;
  return table;
};
