const AC_GAME_OBJECTS = [];//存储所有游戏对象


export class AcGameObject {
    constructor() {
        AC_GAME_OBJECTS.push(this);
        this.timedelta = 0;  //时间间隔
        this.has_called_start = false; //是否执行了start函数
    }

    start() { //只执行一次

    }

    update() {  //每一帧执行一次， 除了第一帧之外

    }

    on_destroy() {  //删除之前执行

    }

    destroy() {
        this.on_destroy();
        for(let i in AC_GAME_OBJECTS) {
            const obj = AC_GAME_OBJECTS[i];
            if(obj === this) {
                AC_GAME_OBJECTS.splice(i);// splice(i)：从索引i开始，删除数组中后续的所有元素
                break;
            }
        }
    }
} 


let last_timestamp;
const step = timestamp => {
    for(let obj of AC_GAME_OBJECTS) { //在js中for循环of ->值、in->下标
        if(!obj.has_called_start) {
            obj.has_called_start = true;
            obj.start(); 
        } else {
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }

    last_timestamp = timestamp;
    requestAnimationFrame(step)
}

requestAnimationFrame(step)