$(function () {
    var page = {
        foodList : {}
    };

    page.init = function () {
        this.event();
    };

    page.event = function () {
        var _this = this;
        $('#headTab li').click(function (e) {
            var _this = $(this);
            $('#headTab .li1').removeClass('li1');
            _this.addClass('li1');

            $('.pagePanel').hide();
            $('#'+_this.data('tab')).show();
        });

        $('[data-food-type-id]').click(function () {
            var _this = $(this);
            $('#foodTypeName').html(_this.data('food-type-name'));
            $('.foodTypeList').hide();
            $('.foodTypeList_'+_this.data('food-type-id')).show();
        });

        $('[data-food-id] .icon_jh').click(function () {
            _this.addFood($(this).closest('[data-food-id]').data());
        });
        $('[data-food-id] .icon_jh1').click(function () {
            _this.reduceFood($(this).closest('[data-food-id]').data());
        });

        $('#cartIcon').click(function () {
            $('#cartPanel').toggleClass('weui_mask');
        });

        $('#clear').click(function () {
            for(var key in _this.foodList){
                _this.reduceFood({
                    foodId : key
                }, 0);
            }
        });

        $('#submit').click(function () {
            var foodForm = $('#form')[0];
            var foodList = [];
            for(var key in _this.foodList){
                _this.foodList[key].price = _this.foodList[key].price * _this.foodList[key].number;
                foodList.push(_this.foodList[key]);
            }

            if(foodList.length == 0){
                alert('请至少选择一道菜肴');
            }else{
                Dolphin.cookie("foodList", Dolphin.json2string(foodList));
                Dolphin.goUrl("/order/orderConfirm");
            }
        });
    };
    page.addFood = function (data, number) {
        var _this = this;
        var id = data.foodId,
            name = data.foodName,
            price = data.foodPrice;

        if(_this.foodList[id]){
            if(number){
                _this.foodList[id].number = number;
            }else{
                _this.foodList[id].number++;
            }
        }else{
            _this.foodList[id] = {
                id : id,
                name : name,
                price : price,
                number : number==null?1:number
            }
        }

        var foodPanel = $('[data-food-id="'+id+'"]');
        foodPanel.find('.sz,.icon_jh1').show();
        foodPanel.find('.jg_b').addClass('jg');
        foodPanel.find('.sz').html(_this.foodList[id].number);

        _this.modifyFood();
        return this;
    };
    page.reduceFood = function (data, number) {
        var _this = this;
        var id = data.foodId;

        if(number != null){
            _this.foodList[id].number = number;
        }else{
            _this.foodList[id].number--;
        }

        var foodPanel = $('[data-food-id="'+id+'"]');
        foodPanel.find('.sz').html(_this.foodList[id].number);
        if(_this.foodList[id].number == 0){
            foodPanel.find('.jg_b').removeClass('jg');
            foodPanel.find('.sz,.icon_jh1').hide();
            delete _this.foodList[id];
        }

        _this.modifyFood();
        return this;
    };
    page.modifyFood = function () {
        var _this = this;
        var totalNumber = 0, totalPrice = 0,
            li,title,price,btn,icon_add,number,icon_reduce,
            cartListPanel = $('#cartList'),
            key;

        cartListPanel.empty();
        for(key in _this.foodList){
            totalNumber += _this.foodList[key].number;
            totalPrice += _this.foodList[key].number * _this.foodList[key].price;

            li = $('<li class="bh">').data({
                "foodId" : _this.foodList[key].id,
                "foodName" : _this.foodList[key].name,
                "foodPrice" : _this.foodList[key].price,
            }).appendTo(cartListPanel);
            title = $('<div class="title_a">').html(_this.foodList[key].name).appendTo(li);
            price = $('<div class="title_c_text">').html('￥' + (_this.foodList[key].price * _this.foodList[key].number).toFixed(2)).appendTo(li);
            btn = $('<div class="btn_gm">').appendTo(li);
            icon_add = $('<div class="icon_jhh">').click(function () {
                _this.addFood($(this).closest('li.bh').data());
            }).appendTo(btn);
            number = $('<div class="sz">').html(_this.foodList[key].number).appendTo(btn);
            icon_reduce = $('<div class="icon_jhh1">').click(function () {
                _this.reduceFood($(this).closest('li.bh').data());
            }).appendTo(btn);
        }
        $('#cartTotalNumber').html(totalNumber);
        $('#cartTotalPrice').html(totalPrice.toFixed(2));
    };

    page.init();
    window.page = page;
});