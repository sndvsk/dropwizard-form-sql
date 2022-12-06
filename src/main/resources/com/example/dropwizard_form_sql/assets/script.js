urlApiSubmit = "/api/userData";
urlApiGetSectors = "/api/sectors";

var firstLoad = true;

// so sectors do not disappear from webpage
function onLoad() {
    if (!firstLoad) {
        return;
    }
    firstLoad = false;
    readSectors();
}

// submit data to the backend after clicking the button
function submitData() {
    var form = document.querySelector('#form1');

    // prevent form from emptying (instead of @GET on the backend)
    function handleForm(event) { event.preventDefault(); }
    form.addEventListener('submit', handleForm);

    // hide errors if there were before (after at least one form submit)
    hideVisibleErrors();

    //validateCheckbox();

    // payload
    var req = {
        'name': form.name.value,
        'uid': null,
        'terms': form.terms.checked,
        'sectors': []
    }

    // add selected sectors to payload
    for (idx = 0; idx < form.sectors.selectedOptions.length; idx++) {
        req.sectors.push(form.sectors.selectedOptions.item(idx).value);
    }

    // if the method is post, then post, if put, then put
    var method = "POST";
    var urlSubmit = urlApiSubmit;
    if (form.uid.value !== "") {
        method = "PUT";
        urlSubmit = urlApiSubmit + "/" + form.uid.value;
        req.uid = form.uid.value;
    } else {
        // so there will be no uid in the payload
        delete req.uid;
    }
    var reqHttpMethod = new XMLHttpRequest();
    reqHttpMethod.open(method, urlSubmit, true);
    reqHttpMethod.onreadystatechange = function() {
        if (this.readyState == 4) {
            onCreateUserDataCompleted(this.status,
                this.responseText,
                this.getResponseHeader('Location'), form);
        }
    }
    reqHttpMethod.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    var reqPayload = JSON.stringify(req)
    reqHttpMethod.send(reqPayload);

}

// read sectors from the database
function readSectors() {
    var reqHttpGet = new XMLHttpRequest();
    reqHttpGet.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            onSectorsRecieved(JSON.parse(this.responseText));
        }
    }
    reqHttpGet.open("GET", urlApiGetSectors, true);
    reqHttpGet.send();
}

// add sectors from the database
function addOption(listbox, text, value, cssClass) {
    var item = document.createElement("OPTION");
    item.text = text;
    item.value = value;
    item.className = cssClass;
    listbox.options.add(item);
}

// add sectors from the database
// optionGroup_level so the sectors are displayed nicely
function onSectorsRecieved(sectors) {
    var form = document.querySelector('#form1');
    for (var i = 0; i < sectors.length; i++) {
        addOption(form.sectors,
            sectors[i].sector_value,
            sectors[i].api_id,
            'optionGroup_level' + sectors[i].level);
    }
}

//
function onCreateUserDataCompleted(status, responseText, responseLocation, form) {
    // if user was created
    if (status == 201) {

        console.log("created at " + responseLocation);
        // set uid
        form.uid.value = responseLocation.split(urlApiSubmit)[1].replace('/','');

        // if there are errors then display them
    } else if (status == 400) {
        var errors = JSON.parse(responseText);
        errorMessage(errors);
    }
}

// remove previous errors
function hideVisibleErrors() {
    const {errorNameField, errorSectorsField, errorTermsField, errorsApiField} = getErrorFieldNames();

    errorNameField.textContent="";
    errorSectorsField.textContent="";
    errorTermsField.textContent="";
    errorsApiField.textContent="";
}

// if there is error for the field, then display it
function checkAndSetErrorMessage(errors, checkFieldName, field) {
    if (errors.some(i => i.fieldName === checkFieldName)) {
        var index = errors.findIndex(i => i.fieldName === checkFieldName);
        field.textContent = errors[index].errorMessage;
    }
}

// add errormessages to the webpage
function errorMessage(errors) {
    const {errorNameField, errorSectorsField, errorTermsField, errorsApiField} = getErrorFieldNames();
    // name -> errorNameField
    // sectors -> errorSectorsField
    // terms -> errorTermsField
    // apiError -> errorsApiField
    var dict = {
        "name" : errorNameField,
        "sectors" : errorSectorsField,
        "terms" : errorTermsField,
        "apiError" : errorsApiField
    }

    for (const [fieldName, errorField] of Object.entries(dict)) {
        checkAndSetErrorMessage(errors, fieldName, errorField);
    }
}

// check if checkbox is checked
function validateCheckbox() {
    if (document.getElementById('terms').checked) {
        console.log("checked");
    } else {
        console.log("not checked");
    }
}

// get elements to fill the errors
function getErrorFieldNames() {
    return {
        errorNameField: getErrorNameField(),
        errorSectorsField: getErrorSectorsField(),
        errorTermsField: getErrorTermsField(),
        errorsApiField: getErrorsApiField()
    };
}

function getErrorNameField() {
    return document.getElementById("errorName");
}

function getErrorSectorsField() {
    return document.getElementById("errorSectors");
}

function getErrorTermsField() {
    return document.getElementById("errorTerms");
}

function getErrorsApiField() {
    return document.getElementById("errorApi");
}
