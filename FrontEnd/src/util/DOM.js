export const $ = (selector) => document.querySelector(selector);
export const addClass = ($el, className) => $el.classList.add(className);
export const removeClass = ($el,className) => $el.classList.remove(className);
export const onClick = ($el,fn) => $el.addEventListner('click', ()=> fn);