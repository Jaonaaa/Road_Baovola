import { removeNavBar } from "../components/Navbar.js";
import { removeMiddlePopUp } from "./PopUp.js";

// STYLE
var styleHider = new CSSStyleSheet();
styleHider.replaceSync(`
#hider {
  background-color: rgba(0, 0, 0, 0.20);
  z-index: 6;
  transition: all 0.2s ease-in-out;
  width: 100%;
  backdrop-filter:blur(1px);
  position: absolute;
  top: 0;
}
`);
// add the style to the document
document.adoptedStyleSheets = [styleHider];

export function createHider() {
  let hiderExistant = document.getElementById("hider");
  if (hiderExistant != undefined) {
    return 0;
  }
  let hider = document.createElement("div");
  hider.setAttribute("id", "hider");
  setUpHider(hider);
  document.body.appendChild(hider);
}

function setUpHider(hider) {
  let heightMax = Math.max(
    document.body.scrollHeight,
    document.body.offsetHeight,
    document.documentElement.clientHeight,
    document.documentElement.scrollHeight,
    document.documentElement.offsetHeight
  );

  hider.style.height = heightMax + "px";
  resizeHider(hider);
  hider.addEventListener("click", () => {
    removeHider();
  });
}

export function removeHider() {
  let hider = document.getElementById("hider");
  if (hider != undefined) {
    hider.style.opacity = 0;
    removeMiddlePopUp();
    removeNavBar();
    setTimeout(() => {
      document.body.removeChild(hider);
    }, 200);
  }
}

function resizeHider(hider) {
  window.addEventListener("resize", () => {
    let heightMax = Math.max(
      document.body.scrollHeight,
      document.body.offsetHeight,
      document.documentElement.clientHeight,
      document.documentElement.scrollHeight,
      document.documentElement.offsetHeight
    );
    hider.style.height = heightMax + "px";
  });
  window.addEventListener("scroll", () => {
    let heightMax = Math.max(
      document.body.scrollHeight,
      document.body.offsetHeight,
      document.documentElement.clientHeight,
      document.documentElement.scrollHeight,
      document.documentElement.offsetHeight
    );
    hider.style.height = heightMax + "px";
  });
}
