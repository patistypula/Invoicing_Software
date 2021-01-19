document.addEventListener("DOMContentLoaded", function () {

    var selectArea  = document.getElementById("search");
    var toReplace   = document.getElementById("toReplace");
    var inputArea   = document.getElementById("insertInput");
    var inputButton = document.getElementById("insertButton");
    var hs          = document.getElementById("hs");

    selectArea.addEventListener('change', function (event){
        if(selectArea.value==='name'){
            var newInsert = document.createElement('input');
            newInsert.setAttribute("name", "searchArea");
            newInsert.type = "text";
            hs.value = "name";
            inputArea.removeChild(inputArea.firstElementChild);
            inputArea.appendChild(newInsert);
        } else if(selectArea.value==='nip'){
            var newInsert = document.createElement('input');
            newInsert.className="input";
            newInsert.setAttribute("name", "searchArea");
            newInsert.type = "text";
            newInsert.placeholder = "XXX-XXX-XX-XX";
            var myRegex = '\\d{3}[-]\\d{3}[-]\\d{2}[-]\\d{2}';
            newInsert.pattern=myRegex;
            hs.value="nip";
            inputArea.removeChild(inputArea.firstElementChild);
            inputArea.appendChild(newInsert);
        } else if(selectArea.value==='regon'){
            var newInsert = document.createElement('input');
            newInsert.setAttribute("name", "searchArea");
            newInsert.type = "text";
            newInsert.placeholder = "XXXXXXXX";
            var myRegex = '\\d{14}|\\d{9}';
            newInsert.pattern=myRegex;
            hs.value="regon";
            inputArea.removeChild(inputArea.firstElementChild);
            inputArea.appendChild(newInsert);
        }
    })

});