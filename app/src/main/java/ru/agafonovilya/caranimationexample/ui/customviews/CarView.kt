package ru.agafonovilya.caranimationexample.ui.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import ru.agafonovilya.caranimationexample.R

class CarView : View {
    // Цвет машины
    private var carColor = Color.BLUE

    // Цвет колес
    private var wheelsColor = Color.BLACK

    // Изображение машины - прямоугольник
    private var carRectangle = RectF()

    // Изображение колеса - прямоугольник
    private var wheel1Rectangle = RectF()
    private var wheel2Rectangle = RectF()
    private var wheel3Rectangle = RectF()
    private var wheel4Rectangle = RectF()

    // "Краска" машины
    private lateinit var carPaint: Paint

    // "Краска" колес
    private lateinit var wheelsPaint: Paint

    // Ширина элемента
    private var carWidth = 0

    // Высота элемента
    private var carHeight = 0


    constructor(context: Context?) : super(context) {
        init()
    }

    // Вызывается при добавлении элемента в макет
    // AttributeSet attrs - набор параметров, указанных в макете для этого элемента
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        initAttr(context, attrs)
        init()
    }

    // Вызывается при добавлении элемента в макет с установленными стилями
    // AttributeSet attrs - набор параметров, указанных в макете для этого элемента
    // int defStyleAttr - базовый установленный стиль
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initAttr(context, attrs)
        init()
    }

    // Вызывается при добавлении элемента в макет с установленными стилями
    // AttributeSet attrs - набор параметров, указанных в макете для этого элемента
    // int defStyleAttr - базовый установленный стиль
    // int defStyleRes - ресурс стиля, если он не был определен в предыдущем параметре
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initAttr(context, attrs)
        init()
    }

    // Инициализация атрибутов пользовательского элемента из xml
    private fun initAttr(
        context: Context,
        attrs: AttributeSet?
    ) {
        with(context.obtainStyledAttributes(attrs, R.styleable.CarView, 0, 0)) {

            // Чтобы получить какое-либо значение из этого массива,
            // надо вызвать соответсвующий метод, и передав в этот метод имя ресурса
            // указанного в файле определения атрибутов (attrs.xml)
            carColor =
                getColor(R.styleable.CarView_car_color, Color.RED)

            // вторым параметром идет значение по умолчанию, если атрибут не указан в макете,
            // то будет подставлятся эначение по умолчанию.
            // Обратите внимание, что первый параметр пишется особым способом
            // через подчерки. первое слово означает имя определения
            // <declare-styleable name="CarView">
            // следующее слово имя атрибута
            wheelsColor =
                getColor(R.styleable.CarView_wheels_color, Color.BLACK)

            // В конце работы дадим сигнал,
            // что нам больше массив со значениями атрибутов не нужен
            // Система в дальнейшем будет переиспользовать этот объект,
            // и мы никогда не получим к нему доступ из этого элемента
            recycle()
        }
    }

    // Начальная инициализация полей класса
    private fun init() {
        carPaint = Paint().run {
            color = carColor
            style = Paint.Style.FILL
            this
        }

        wheelsPaint = Paint().run {
            color = wheelsColor
            style = Paint.Style.FILL
            this
        }

    }

    // Когда система Андроид создает пользовательский экран, то еще неизвестны размеры элемента.
    // Это связанно с тем, что используются расчетные единица измерения,
    // то есть, чтобы элемент выглядил одинаково на разных усройствах,
    // необходимы расчеты размера элемента, в привязке к размеру экрана, его разрешения и плотности пикселей.
    // Этот метод вызывается при необходимости изменения размера элемента
    // Такая необходимость возникает каждый раз при отрисовке экрана.
    // Если нам надо нарисовать свой элемент, то переопределяем этот метод,
    // и задаем новые размеры нашим элементам внутри view
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Получить реальные ширину и высоту
        carWidth = w - paddingLeft - paddingRight
        carHeight = h - paddingTop - paddingBottom

        // Wheels size
        val wheelWidth = carWidth / 6
        val wheelHeight = carHeight / 6

        // Отрисовка машины
        carRectangle = RectF(0f, wheelHeight.toFloat(), carWidth.toFloat(),(carHeight - wheelHeight).toFloat())
        wheel1Rectangle = RectF((carWidth/6).toFloat(), 0f, (carWidth - (carWidth/6*4)).toFloat(), wheelHeight.toFloat() )
        wheel2Rectangle = RectF((carWidth/6*4).toFloat(), 0f, (carWidth - (carWidth/6)).toFloat(), wheelHeight.toFloat() )
        wheel3Rectangle = RectF((carWidth/6).toFloat(), (height - wheelHeight).toFloat(), (carWidth - (carWidth/6*4)).toFloat(), carHeight.toFloat() )
        wheel4Rectangle = RectF((carWidth/6*4).toFloat(), (height - wheelHeight).toFloat(), (carWidth - (carWidth/6)).toFloat(), carHeight.toFloat()  )
    }

    // Вызывается, когда надо нарисовать элемент
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        with(canvas) {
            drawRect(carRectangle, carPaint)
            drawRect(wheel1Rectangle, wheelsPaint)
            drawRect(wheel2Rectangle, wheelsPaint)
            drawRect(wheel3Rectangle, wheelsPaint)
            drawRect(wheel4Rectangle, wheelsPaint)
        }
    }

}