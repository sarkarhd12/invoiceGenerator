
function addItem() {
    const itemsContainer = document.getElementById('itemsContainer');
    const itemRow = document.createElement('div');
    itemRow.classList.add('itemRow');
    itemRow.innerHTML = `
        <label for="itemDescription">Description:</label>
        <input type="text" class="itemDescription" name="itemDescription[]" required>
        <label for="itemUnitPrice">Unit Price:</label>
        <input type="number" step="0.01" class="itemUnitPrice" name="itemUnitPrice[]" required>
        <label for="itemQuantity">Quantity:</label>
        <input type="number" class="itemQuantity" name="itemQuantity[]" required>
        <label for="itemDiscount">Discount:</label>
        <input type="number" step="0.01" class="itemDiscount" name="itemDiscount[]" required>
        <button type="button" onclick="removeItem(this)">Remove</button>
    `;
    itemsContainer.appendChild(itemRow);
}


function removeItem(button) {
    const itemRow = button.parentElement;
    itemRow.remove();
}


document.getElementById('invoiceForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData();
  
    formData.append('sellerName', document.getElementById('sellerName').value);
    formData.append('sellerAddress', document.getElementById('sellerAddress').value);
    formData.append('sellerCity', document.getElementById('sellerCity').value);
    formData.append('sellerState', document.getElementById('sellerState').value);
    formData.append('sellerPincode', document.getElementById('sellerPincode').value);
    formData.append('sellerPanNo', document.getElementById('sellerPanNo').value);
    formData.append('sellerGstRegistrationNo', document.getElementById('sellerGstRegistrationNo').value);

    formData.append('orderNo', document.getElementById('orderNo').value);
    formData.append('orderDate', document.getElementById('orderDate').value);

    
    formData.append('invoiceNo', document.getElementById('invoiceNo').value);
    formData.append('invoiceDate', document.getElementById('invoiceDate').value);
    formData.append('reverseCharge', document.getElementById('reverseCharge').checked);

    
    formData.append('billingName', document.getElementById('billingName').value);
    formData.append('billingAddress', document.getElementById('billingAddress').value);
    formData.append('billingCity', document.getElementById('billingCity').value);
    formData.append('billingState', document.getElementById('billingState').value);
    formData.append('billingPincode', document.getElementById('billingPincode').value);
    formData.append('billingStateUTCode', document.getElementById('billingStateUTCode').value);


    formData.append('shippingName', document.getElementById('shippingName').value);
    formData.append('shippingAddress', document.getElementById('shippingAddress').value);
    formData.append('shippingCity', document.getElementById('shippingCity').value);
    formData.append('shippingState', document.getElementById('shippingState').value);
    formData.append('shippingPincode', document.getElementById('shippingPincode').value);
    formData.append('shippingStateUTCode', document.getElementById('shippingStateUTCode').value);

    const itemRows = document.querySelectorAll('.itemRow');
    itemRows.forEach((itemRow, index) => {
        formData.append(`itemDescription[${index}]`, itemRow.querySelector('.itemDescription').value);
        formData.append(`itemUnitPrice[${index}]`, itemRow.querySelector('.itemUnitPrice').value);
        formData.append(`itemQuantity[${index}]`, itemRow.querySelector('.itemQuantity').value);
        formData.append(`itemDiscount[${index}]`, itemRow.querySelector('.itemDiscount').value);
    });


    formData.append('logo', document.getElementById('logo').files[0]);
    formData.append('signature', document.getElementById('signature').files[0]);

    
    fetch('http://localhost:8080/invoice/generate', {
        method: 'POST',
        body: formData,
        headers: {
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

