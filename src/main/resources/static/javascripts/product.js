function validateProductName() {
    var hasErrors = true;
    var productNameSelector = document.getElementById("product-name");
    var productNameErrorSelector = document.getElementById("product-name-error");
    var productName = productNameSelector.value;
    if (productName.length === 0) {
        updateErrorCss(productNameSelector, productNameErrorSelector, "Product Name cannot be empty");
    } else if (productName.length > 0) {
        removeErrorCss(productNameSelector, productNameErrorSelector);
        hasErrors = false;
    }
    return hasErrors;
}

function validateProductPrice() {
    var hasErrors = true;
    var productPriceErrorSelector = document.getElementById("product-price-error");
    var productPriceSelector = document.getElementById("product-price");
    var productPrice = productPriceSelector.value;
    var isNum = (/^\d+$/.test(productPrice));
    if (productPrice.length === 0)
        updateErrorCss(productPriceSelector, productPriceErrorSelector, "Product Price cannot be empty");
    else if (!isNum)
        updateErrorCss(productPriceSelector, productPriceErrorSelector, "Product Price cannot contains non-numeric characters");
    else if (productPrice < 0)
        updateErrorCss(productPriceSelector, productPriceErrorSelector, "Product Price cannot be less than 0");
    else {
        removeErrorCss(productPriceSelector, productPriceErrorSelector);
        hasErrors = false;
    }
    return hasErrors;
}

function updateErrorCss(fieldSelector, errorSelector, message) {
    fieldSelector.style.boxShadow = "1px 1px 5px red";
    fieldSelector.style.border = "1px solid red";
    errorSelector.style.display = "block";
    errorSelector.innerText = message;
}

function removeErrorCss(fieldSelector, errorSelector) {
    fieldSelector.style.boxShadow = "1px 1px 5px deepskyblue";
    fieldSelector.style.border = "1px solid deepskyblue";
    errorSelector.style.display = "none";
}

function validateForm() {
    return !(validateProductPrice() && validateProductName());
}