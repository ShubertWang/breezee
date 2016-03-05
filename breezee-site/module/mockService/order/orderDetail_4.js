var data = {
    id : "1",
    status : "4",
    type : 'takeout',
    createTime : 1451534400000,
    restaurant : {
        id : "1",
        name : "索迪斯微软食堂一号"
    },
    name : '赵小姐',
    phone : '18688883456',
    address : '天天小区40号101',
    payType : 'wx',
    time : 1451534400000,
    price : '108.00',
    food : [
        {
            food : {
                id : 1,
                name : '农家小炒肉'
            },
            number : 1,
            price : 22.00
        }
    ],
    otherCost : [
        {
            id : 1,
            name : '配送费',
            number : 1,
            price : '8.00'
        },{
            id : 2,
            name : '餐盒费',
            number : 2,
            price : '2.00'
        }
    ]
};

module.exports = data;