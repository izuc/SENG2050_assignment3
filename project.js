function process_request(file, parameters, fn) {
    $.ajax({  
        type: 'POST',
        url: file,
        cache: false,
        data: parameters,
        success: fn
    });
}

function submit_response(result) {
    var json = jQuery.parseJSON(result);
    $('#info_message').animate({
        "height": "show"
    }, {
        duration: 1000
    });
    $('#info_message').html(json.message);
    $('#info_message').delay(2000).animate({
        "height": "hide"
    }, {
        duration: 1000
    });
}