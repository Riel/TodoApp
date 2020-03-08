function addInstant() {
    var title = $('#title').val();
    if (title != null && title != '') {
        $("#add-form").attr("action","/todo/add-instant");
        $("#add-form").submit()
    }else{
        $("#add-todo").click();
        return false;
    }
}