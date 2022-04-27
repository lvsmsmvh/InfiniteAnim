# InfiniteAnim

A library that helps you to use infinite animations on your buttons, text views, layouts without worrying about bugs.
To use it, add this repository into your project-level gradle:

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    
And this dependency into your app-level gradle:

    dependencies {
        ...
        implementation 'com.github.lvsmsmvh:InfiniteAnim:1.0.0'
    }
    
Then, start to use animations in a Fragment, Ativity or any other lifecycle-aware components with the following  code:

    class ExampleActivity : AppCompatActivity() {

        private val anim by infiniteAnim()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_example)

            val button = findViewById<Button>(R.id.button1)
            anim.enlargeSmallOn(button, startOffset = 1_000L, repeatDelay = 500L)
        }
    }
