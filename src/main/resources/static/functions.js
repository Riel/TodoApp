function deleteTodo(id) {
    var owner = $('#owner').val();
    var context = $('#context').val();
    var project = $('#project').val();
    $(location).attr('href','/todo/id/'+id+'/owner/'+owner+'/project/'+project+'/context/'+context+'/delete');
}


function completeTodo(id) {
    var owner = $('#owner').val();
    var context = $('#context').val();
    var project = $('#project').val();
    $(location).attr('href','/todo/id/'+id+'/owner/'+owner+'/project/'+project+'/context/'+context+'/done');
}