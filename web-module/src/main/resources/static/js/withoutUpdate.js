$(document).ready(function () {
    $('#btn1').click(function () {
        var fd = $('#btn1').val();
        $.ajax({
            type: 'POST',
            url: "enable",
            data: {id: fd},
            success: function (result) {
                $('#result').html(result)
            }
        })
    });
    $('#btn2').click(function () {
        var fd = $('#btn2').val();
        $.ajax({
            type: 'POST',
            url: "disable",
            data: {id: fd},
            success: function (result) {
                $('#result').html(result)
            }
        });

    });

});