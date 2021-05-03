document.addEventListener("DOMContentLoaded", function () {

    var bruttoValue  = document.getElementById("brutto");
    var nettoValue   = document.getElementById("netto");
    var vatValue     = document.getElementById("vat");
    var taxaValue    = document.getElementById("taxa");
//    var kindInvoicing = document.getElementById("kindInvoicing");

    nettoValue.addEventListener('change', function(event){
        setBruttoValue();
        console.log("netto = "+nettoValue.value)
    });

    vatValue.addEventListener('change', function(event){
       setBruttoValue();
       console.log("vat = "+vatValue.value);
    });

//    kindInvoicing.addEventListener('change', function(event){
//        if(kindInvoicing.value=='FK'){
//            addArea();
//        }
//    });

    function setBruttoValue(){
        var netto = nettoValue.value;
        var vat   = vatValue.value;
        var brutto = (1+(vat)/100) * netto;
        //console.log("kwota brutto = "+brutto);
        var taxa = brutto - netto;
        bruttoValue.value = brutto.toPrecision(precision(brutto));
        taxaValue.value   = taxa.toPrecision(precision(taxa));
    }

    function precision(how){
        var hm;
        if(how<1){
            hm = 2;
        } else{
            var serch = true;
            var divider = 1;
            var ite = 3;
            while (how/divider>1){
                hm = ite;
                //console.log("bmi = "+how+"divider = "+divider+" wynosi = "+how/divider);
                divider = divider * 10;
                ite ++;
            }
        }
        return hm;
    }

//    function addArea(){
//        var trFK = document.createElement('th:each="advance, iter:${additionals}"');
//        var firstCol = document.createElement('<td th:text="${iter.index+1}"></td>');
//        trFK.appendChild(firstCol);
//        var secondCol = document.createElement('<td th:text="${advance.numberInvoicing}"></td>');
//        trFK.appendChild(secondCol);
//        var thirdCol = document.createElement('<td th:text="${list[iter.index].descryptionSaleProduct}"></td>');
//        trFK.appendChild(thirdCol);
//        var fourthCol = document.createElement('<td th:text="${list[iter.index].netto}"></td>');
//        trFK.appendChild(fourthCol);
//        var fifthCol = document.createElement('<td th:text="${list[iter.index].vat}"></td>');
//        trFK.appendChild(fifthCol);
//        var sixthCol = document.createElement('<td th:text="${list[iter.index].brutto}"></td>');
//        trFK.appendChild(sixthCol);
//    }

});