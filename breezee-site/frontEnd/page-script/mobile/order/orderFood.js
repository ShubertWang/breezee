$(function () {
    var page = {
        foodList : {},
        restaurant : null
    };

    page.init = function () {
        this.event();
        this.restaurant = REQUEST_MAP.body;
    };

    page.event = function () {
        var _this = this;
        $('#headTab li').click(function (e) {
            var _this = $(this);
            $('#headTab .li1').removeClass('li1');
            _this.addClass('li1');

            $('.pagePanel').hide();
            $('#'+_this.data('tab')).show();

            if(_this.data('tab') == 'restaurant_info'){
                $('#cartPanel').hide();
            }else{
                $('#cartPanel').show();
            }
        });

        $('div.foodDetail').click(function (e) {
            var food, foodId, number=0,
                i, j;

            foodId = $(this).closest('[data-food-id]').data('food-id');
            for(i = 0; i < _this.restaurant.food.length; i++){
                for(j = 0; j < _this.restaurant.food[i].children.length; j++){
                    if(foodId == _this.restaurant.food[i].children[j].id){
                        food = _this.restaurant.food[i].children[j];
                        break;
                    }
                }
                if(food){
                    break;
                }
            }

            if(_this.foodList[foodId]){
                number = _this.foodList[foodId].number;
            }

            var detailPanel = $('#food_detail');

            detailPanel.find('#food_detail_panel').data({
                'foodId' : food.id,
                'foodName' : food.name,
                'foodPrice' : food.price
            });
            detailPanel.find('div[name="img"]').html('<img src="/custom/wx_style/images/'+food.image+'.png">');
            detailPanel.find('div[name="name"]').html(food.name);
            detailPanel.find('div[name="number"]').html(number);
            detailPanel.find('span[name="price"]').html(food.price);
            detailPanel.find('[name="desc"]').html(food.desc || '暂无');

            $('.pagePanel').hide();
            detailPanel.show();
        });

        $('[data-food-type-id]').click(function () {
            var _this = $(this);
            $('#foodTypeName').html(_this.data('food-type-name'));
            $('.foodTypeList').hide();
            $('.foodTypeList_'+_this.data('food-type-id')).show();
        });

        $('.icon_jh').click(function () {
            _this.addFood($(this).closest('.foodData').data());
        });
        $('.icon_jh1').click(function () {
            _this.reduceFood($(this).closest('.foodData').data());
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

        $('#food_detail').find('div[name="number"]').html(_this.foodList[id].number);

        var foodPanel = $('[data-food-id="'+id+'"]');
        foodPanel.find('.sz,.icon_jh1').show();
        foodPanel.find('.jg_b').addClass('jg');
        foodPanel.find('.icon_jh').removeClass('icon_jhh');
        foodPanel.find('.sz').html(_this.foodList[id].number);

        _this.modifyFood();
        return this;
    };
    page.reduceFood = function (data, number) {
        var _this = this;
        var id = data.foodId;

        if(_this.foodList[id] == null){
            return this;
        }

        if(number != null){
            _this.foodList[id].number = number;
        }else{
            _this.foodList[id].number--;
        }

        $('#food_detail').find('div[name="number"]').html(_this.foodList[id].number);

        var foodPanel = $('[data-food-id="'+id+'"]');
        foodPanel.find('.sz').html(_this.foodList[id].number);
        if(_this.foodList[id].number == 0){
            foodPanel.find('.jg_b').removeClass('jg');
            foodPanel.find('.icon_jh').addClass('icon_jhh');
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