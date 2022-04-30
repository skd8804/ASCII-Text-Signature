package signature

import java.io.File

class Person(val fullName: MutableList<String>, val title: MutableList<String>) {

    fun printName() {
        val frame = "88"


        val topText = mutableListOf<String>()
        val botText = mutableListOf<String>()


//        create top text list
        for (i in 0..fontHeight(romanContent)-1) {
            for (j in fullName) {
                //  print(romanFont[j]!![i])
                topText.add(romanFont[j]!![i])

            }

        }
//        create bottom text list
        for (i in 0..fontHeight(mediumContent)-1) {
            for (j in title) {
                //   print(mediumFont[j]!![i])
                botText.add(mediumFont[j]!![i])
            }

        }

//        top frame print
        val printFrame ="8".repeat(sizeList(topText, botText)) + frame.repeat(4)
        println(printFrame)



//        iterate and print text
        var topCount = 0
        for (i in topText.indices) {
            if (topCount == 0) {
                print( frame + " ".repeat(spaceCalcStartTop(printFrame,topText,fullName)) )
            }
            topCount++
            print(topText[i])
            if (topCount % fullName.size == 0) {
                if (topText.lastIndex != i) {
                    println(" ".repeat(spaceCalcEnd(printFrame,topText,fullName)) + frame )
                    print(frame + " ".repeat(spaceCalcStartTop(printFrame,topText,fullName)) )
                } else println(" ".repeat(spaceCalcEnd(printFrame,topText,fullName)) + frame )
            }
        }
//      iterate and print bottom text
        var botCount = 0
        for (i in botText.indices) {
            if (botCount == 0) {
                print(frame + " ".repeat(spaceCalcStartBot(printFrame,botText,title)) )
            }
            botCount++
            print(botText[i])
            if (botCount % title.size == 0) {
                if (botText.lastIndex != i) {
                    println(" ".repeat(spaceCalcEnd(printFrame, botText, title)) + frame)
                    print(frame + " ".repeat(spaceCalcStartBot(printFrame, botText, title)))
                } else {
                    println(" ".repeat(spaceCalcEnd(printFrame, botText, title)) + frame)

                }
            }
        }
        //        bot frame print
        println(printFrame)

    }
    private fun sizeList(topText: MutableList<String>, botText: MutableList<String>, name: MutableList<String> = fullName, second: MutableList<String> = title): Int {

        val topSub = topText.subList(0, name.size).joinToString("").length
        val botSub = botText.subList(0, second.size).joinToString("").length

        if (topSub >= botSub ) return  topSub
        return botSub
    }

    private fun whichIsBigger (name: MutableList<String> = fullName, second: MutableList<String> = title): String {
        val topText = mutableListOf<String>()
        val botText = mutableListOf<String>()
        for (i in 0..fontHeight(romanContent)-1) {
            for (j in fullName) {
                //  print(romanFont[j]!![i])
                topText.add(romanFont[j]!![i])

            }
        }
//        create bottom text list
        for (i in 0..fontHeight(mediumContent)-1) {
            for (j in title) {
                //   print(mediumFont[j]!![i])
                botText.add(mediumFont[j]!![i])
            }
        }

        val top = topText.subList(0, fullName.size).joinToString("").length
        val bot = botText.subList(0, title.size).joinToString("").length

        if (top >= bot ) return  "top"
        return "bot"
    }

    private fun sizeText (nameTop: MutableList<String>, title: MutableList<String>): Int {
        var size: Int
        if (nameTop.joinToString("").length > title.size) {
            size = nameTop.joinToString("").length
        } else {
            size = title.joinToString("").length
        }
        return size
    }

    private fun spaceCalcStartTop (sizeOfFrame: String,
                                   text: MutableList<String>,
                                   lineLength: MutableList<String>): Int
    {
        val length = text.subList(0, lineLength.size).joinToString("").length
        val bigger = whichIsBigger(fullName, title)
        val lengthOfFrame = sizeOfFrame.length - 3
        var extraSpace = (lengthOfFrame - text.subList(0, lineLength.size).joinToString("").length) / 2

        if (bigger == "bot" && 4 + extraSpace + extraSpace + length >  sizeOfFrame.length  ) {
            extraSpace--
        }

        return extraSpace
    }

    private fun spaceCalcStartBot (sizeOfFrame: String,
                                   text: MutableList<String>,
                                   lineLength: MutableList<String>): Int
    {
        val length = text.subList(0, lineLength.size).joinToString("").length
        val bigger = whichIsBigger(fullName, title)
        val lengthOfFrame = sizeOfFrame.length - 3
        var extraSpace = (lengthOfFrame - length ) / 2

        if (bigger == "top" && 4 + extraSpace + extraSpace + length >  sizeOfFrame.length  ) {
            extraSpace--
        }

        return extraSpace
    }

    private fun spaceCalcEnd (sizeOfFrame: String,
                              text: MutableList<String>,
                              lineLength: MutableList<String>): Int
    {
        val lengthOfFrame = sizeOfFrame.length - 3
        var extraSpace = lengthOfFrame - text.subList(0, lineLength.size).joinToString("").length
        if ( extraSpace % 2 != 0) extraSpace = extraSpace / 2   else extraSpace /= 2
        return extraSpace
    }


    val romanFile = File("C:\\Users\\skd\\IdeaProjects\\ASCII Text Signature\\roman.txt")
    val mediumFile = File("C:\\Users\\skd\\IdeaProjects\\ASCII Text Signature\\medium.txt")
    val romanContent = romanFile.readLines().toMutableList()
    val mediumContent = mediumFile.readLines().toMutableList()


    fun fontHeight (list: MutableList<String>): Int {
        return (list[0].split(" ").map { it.toInt() }.toMutableList())[0]
    }

    fun fontCreation(list: MutableList<String>): MutableMap<String, MutableList<String>> {
        val fontCount = (list[0].split(" ").map { it.toInt() }.toMutableList())[1]
        val fontHeight = fontHeight(list)
        val count = list.size - 1
        val fontList = mutableMapOf<String, MutableList<String>>()
        for (i in 1..count step fontHeight + 1) {
            fontList[(list[i].split(" ").map { it.toString() }.toMutableList())[0]] =
                list.subList(i + 1, i + fontHeight + 1)
        }
        fontList[" "] = MutableList(fontHeight) { " ".repeat(list[3].length) }
        return  fontList
    }
    val romanFont = fontCreation(romanContent)


    val mediumFont = fontCreation(mediumContent)

}

fun main() {


    println("Enter name and surname:")
    val name: MutableList<String> = readln().map { it.toString() }.toMutableList()
    println("Enter person's status:")
    val status = readln().map { it.toString().lowercase() }.toMutableList()
    val person = Person(fullName = name, title = status)


    person.printName()




}
