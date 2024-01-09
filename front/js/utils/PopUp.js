export function createSidePopUp(content, status) {
  let popUp = document.createElement("div");
  let timer = 3000;
  popUp.classList.add("popUp-box");
  let icon = document.createElement("div");
  icon.classList.add("status-popUp");
  let container = document.createElement("div");
  container.classList.add("content-popUp");
  if (status == "ok") {
    popUp.classList.add("succesBg");
    icon.classList.add("succesColor");
    container.classList.add("succesColor");
    icon.innerHTML = `<i class="fas fa-check"></i>`;
  } else if (status == "error") {
    popUp.classList.add("errorBg");
    icon.classList.add("errorColor");
    container.classList.add("errorColor");
    icon.innerHTML = `<i class="fas fa-exclamation-triangle"></i>`;
  } else if (status == "image") {
    setTimeout(() => {
      popUp.innerHTML = `<img id="img-product-popUp"src="${content}" alt="image product"/>`;
    }, 5);
    timer += 1200;
  }
  container.innerHTML = content;
  popUp.appendChild(icon);
  popUp.appendChild(container);

  let topOffset = 3;
  if (window.innerWidth < 765) {
    topOffset = 3.5;
  }
  if (getNumberOfSidePopUp() != 0) {
    topOffset = getHeightNecessary() + topOffset;
  }

  popUp.style.top = topOffset + "rem";
  document.body.prepend(popUp);
  setTimeout(() => {
    popUp.style.transform = "translateX(0)";
  }, 20);

  setTimeout(() => {
    popUp.style.transform = "translateX(110%)";
    setTimeout(() => {
      removeMe(popUp);
    }, 250);
  }, timer);
}

function removeMe(object) {
  document.body.removeChild(object);
}
function getNumberOfSidePopUp() {
  let popUps = document.querySelectorAll(".popUp-box");
  return popUps.length;
}

function getHeightNecessary() {
  let height = 0;
  let popUps = document.querySelectorAll(".popUp-box");
  popUps.forEach((popUp) => {
    let h = getComputedStyle(popUp).height.replace("px", "");
    height += +h + 16;
  });
  return height / 16;
}

export function createMiddlePopUp(content) {
  let container = document.createElement("div");
  container.setAttribute("id", "middle-popUp");
  container.appendChild(content);
  createHider();
  document.body.prepend(container);
}

export function removeMiddlePopUp() {
  if (document.getElementById("middle-popUp") != undefined) {
    document.body.removeChild(document.getElementById("middle-popUp"));
  }
}
