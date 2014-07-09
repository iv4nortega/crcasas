function createSlider() {

                $("#slider").slider({
                    range: true,
                    min: 100,
                    max: 500,
                    step: 10,
                    values: [100, 500],
                    slide: function (event, ui) {
                        var delay = function () {

                            var handleIndex = $(ui.handle).index();

                            var label = handleIndex == 1 ? '#min' : '#max';

                            $(label).html('$' + ui.value + 'k').position({
                                my: 'center top',
                                at: 'center bottom',
                                of: ui.handle
                            });
                        };

                        // wait for the ui.handle to set its position
                        setTimeout(delay, 5);
                    }
                });

                $('#min').html('$' + $('#slider').slider('values', 0)).position({
                    my: 'center top ',
                    at: 'center bottom',
                    of: $('#slider a:eq(0)')
                });

                $('#max').html('$' + $('#slider').slider('values', 1)).position({
                    my: 'center top',
                    at: 'center bottom',
                    of: $('#slider a:eq(1)')
                });



}     



       

