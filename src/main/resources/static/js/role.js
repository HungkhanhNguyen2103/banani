let role = document.getElementById("chucVu").innerText.trim();
console.log('abc');
document.querySelectorAll("ul.menu-bar li").forEach(li => {
    li.classList.add("d-none");
});

if (role === "Quản lý") {
    document.querySelectorAll("li.quanly").forEach(li => {
        li.classList.remove("d-none");
    });
}

if (role === "Thu ngân") {
    document.querySelectorAll("li.thuNgan").forEach(li => {
        li.classList.remove("d-none");
    });
}

if (role === "Phục vụ") {
    document.querySelectorAll("li.phucVu").forEach(li => {
        li.classList.remove("d-none");
    });
}