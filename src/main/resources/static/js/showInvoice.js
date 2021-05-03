document.addEventListener("DOMContentLoaded", function () {
    var bruttoValue  = document.getElementById("brutto");
    var nettoValue   = document.getElementById("netto");
    var vatValue     = document.getElementById("vat");
    var taxaValue    = document.getElementById("taxa");

    var netto = nettoValue.value;
    var vat   = vatValue.value;
    var brutto = (1+(vat)/100) * netto;
    var taxa = brutto - netto;
    bruttoValue.value = brutto.toPrecision(precision(brutto));
    taxaValue.value   = taxa.toPrecision(precision(taxa));

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
                divider = divider * 10;
                ite ++;
            }
        }
        return hm;
    }
});