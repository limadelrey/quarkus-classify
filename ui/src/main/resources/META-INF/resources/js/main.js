// Open modal on index.html
$('#myModal').on('shown.bs.modal', function () {
  $('#myInput').trigger('focus')
})