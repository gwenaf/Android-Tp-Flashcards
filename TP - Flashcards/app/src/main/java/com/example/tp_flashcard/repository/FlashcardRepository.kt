package com.example.tp_flashcard.repository

import android.content.Context
import androidx.room.Room
import com.example.tp_flashcard.model.Flashcard
import com.example.tp_flashcard.model.FlashcardCategory

object FlashcardRepository {
    private lateinit var database: FlashcardDatabase

    fun initDatabase(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            FlashcardDatabase::class.java,
            "flashcard-db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration(false)
            .build()

        val dao = database.flashcardDao()
        if (dao.countFlashCards() == 0) {
            // 12 catégories
            val defaultCategories = listOf(
                FlashcardCategory(1,  name = "Mathématiques"),
                FlashcardCategory(2,  name = "Science"),
                FlashcardCategory(3,  name = "History"),
                FlashcardCategory(4,  name = "Literature"),
                FlashcardCategory(5,  name = "Géographie"),
                FlashcardCategory(6,  name = "Art"),
                FlashcardCategory(7,  name = "Musique"),
                FlashcardCategory(8,  name = "Sports"),
                FlashcardCategory(9,  name = "Films"),
                FlashcardCategory(10, name = "Technologie"),
                FlashcardCategory(11, name = "Langues"),
                FlashcardCategory(12, name = "Philosophie")
            )

            // Une dizaine de questions par catégorie
            val defaultFlashcards = listOf(
                // Math
                Flashcard( 1, 1, "Combien font 8 × 7 ?", "56"),
                Flashcard( 2, 1, "Quelle est la dérivée de x² ?", "2x"),
                Flashcard( 3, 1, "Résous l'équation : 2x + 3 = 11", "x = 4"),
                Flashcard( 4, 1, "Quelle est la valeur de π approximée à deux décimales ?", "3,14"),
                Flashcard( 5, 1, "Quel est le carré de 15 ?", "225"),

                // Science
                Flashcard( 6, 2, "Quel est le symbole chimique du fer ?", "Fe"),
                Flashcard( 7, 2, "Combien de planètes composent le système solaire ?", "8"),
                Flashcard( 8, 2, "Quel est le gaz le plus abondant dans l'atmosphère terrestre ?", "Azote (N₂)"),
                Flashcard( 9, 2, "Quel organe produit l'insuline ?", "Le pancréas"),
                Flashcard(10, 2, "Quelle unité mesure l'intensité du courant électrique ?", "L'ampère (A)"),

                // History
                Flashcard(11, 3, "En quelle année a eu lieu la Révolution française ?", "1789"),
                Flashcard(12, 3, "Qui a découvert l'Amérique en 1492 ?", "Christophe Colomb"),
                Flashcard(13, 3, "Quel mur est tombé en 1989 ?", "Le mur de Berlin"),
                Flashcard(14, 3, "Qui était le premier empereur romain ?", "Auguste"),
                Flashcard(15, 3, "Quelle dynastie a régné en Angleterre de 1485 à 1603 ?", "Les Tudors"),

                // Literature
                Flashcard(16, 4, "Qui a écrit 'Les Misérables' ?", "Victor Hugo"),
                Flashcard(17, 4, "Quel dramaturge anglais a écrit 'Hamlet' ?", "William Shakespeare"),
                Flashcard(18, 4, "Quel roman débute par 'Call me Ishmael.' ?", "Moby Dick"),
                Flashcard(19, 4, "Qui est l'auteur de '1984' ?", "George Orwell"),
                Flashcard(20, 4, "Quel poète français a écrit 'Le Dormeur du val' ?", "Arthur Rimbaud"),

                // Geography
                Flashcard(21, 5, "Quelle est la capitale de l'Australie ?", "Canberra"),
                Flashcard(22, 5, "Quel est le plus long fleuve du monde ?", "Le Nil"),
                Flashcard(23, 5, "Quel désert est le plus grand sur Terre ?", "Sahara"),
                Flashcard(24, 5, "Quelle montagne est la plus haute ?", "L'Everest"),
                Flashcard(25, 5, "Quel pays a la plus grande population ?", "La Chine"),

                // Art
                Flashcard(26, 6, "Qui a peint La Joconde ?", "Léonard de Vinci"),
                Flashcard(27, 6, "Quel mouvement artistique est associé à Monet ?", "L'impressionnisme"),
                Flashcard(28, 6, "Qui a sculpté David ?", "Michel-Ange"),
                Flashcard(29, 6, "Quel peintre espagnol est connu pour son style cubiste ?", "Pablo Picasso"),
                Flashcard(30, 6, "Quel courant artistique a débuté à la fin du XIXᵉ siècle et explore le subconscient ?", "Le surréalisme"),

                // Music
                Flashcard(31, 7, "Qui a composé la Neuvième Symphonie ?", "Beethoven"),
                Flashcard(32, 7, "Quel groupe a chanté 'Bohemian Rhapsody' ?", "Queen"),
                Flashcard(33, 7, "Quel instrument a 88 touches ?", "Le piano"),
                Flashcard(34, 7, "Quel style musical est né à la Nouvelle-Orléans ?", "Le jazz"),
                Flashcard(35, 7, "Qui est le 'Roi de la Pop' ?", "Michael Jackson"),

                // Sports
                Flashcard(36, 8, "Combien de joueurs par équipe au football ?", "11"),
                Flashcard(37, 8, "Quel sport utilise un shuttlecock ?", "Le badminton"),
                Flashcard(38, 8, "En quelle année ont eu lieu les Jeux olympiques de Tokyo ?", "2021"),
                Flashcard(39, 8, "Quel coureur a 7 titres au Tour de France ?", "Lance Armstrong (démérité)"),
                Flashcard(40, 8, "Dans quel sport utilise-t-on un time-out ?", "Le basket-ball"),

                // Movies
                Flashcard(41, 9, "Quel film débute par 'Il était une fois en Amérique' ?", "Once Upon a Time in America"),
                Flashcard(42, 9, "Qui a réalisé 'Inception' ?", "Christopher Nolan"),
                Flashcard(43, 9, "Quel film a remporté l'Oscar du meilleur film en 1994 ?", "Forrest Gump"),
                Flashcard(44, 9, "Qui joue Jack Dawson dans Titanic ?", "Leonardo DiCaprio"),
                Flashcard(45, 9, "Quel film se passe dans une galaxie lointaine, très lointaine ?", "Star Wars"),

                // Technology
                Flashcard(46, 10, "Que signifie CPU ?", "Central Processing Unit"),
                Flashcard(47, 10, "Quel langage est utilisé pour Android ?", "Kotlin"),
                Flashcard(48, 10, "Que désigne 'HTTP' ?", "HyperText Transfer Protocol"),
                Flashcard(49, 10, "Qu'est-ce qu'un SSD ?", "Un disque à mémoire flash"),
                Flashcard(50, 10, "Quel système d'exploitation est open source ?", "Linux"),

                // Language
                Flashcard(51, 11, "Comment dit-on 'Bonjour' en japonais ?", "Konnichiwa"),
                Flashcard(52, 11, "Quel mot anglais signifie 'maison' ?", "House"),
                Flashcard(53, 11, "Quel est le pluriel de 'mouse' ?", "Mice"),
                Flashcard(54, 11, "Comment traduit-on 'amour' en espagnol ?", "Amor"),
                Flashcard(55, 11, "Quel caractère accentué n'existe pas en anglais ?", "é"),

                // Philosophy
                Flashcard(56, 12, "Qui a écrit 'Le Discours de la méthode' ?", "René Descartes"),
                Flashcard(57, 12, "Quel philosophe grec est l'élève de Socrate ?", "Platon"),
                Flashcard(58, 12, "Qui a dit 'Je pense, donc je suis' ?", "Descartes"),
                Flashcard(59, 12, "Quel courant philosophique valorise la liberté individuelle ?", "L'existentialisme"),
                Flashcard(60, 12, "Quel philosophe est connu pour son concept de 'Volonté de puissance' ?", "Nietzsche")
            )

            dao.insertCategories(defaultCategories)
            dao.insertFlashCards(defaultFlashcards)
        }
    }

    fun getAllCategories(): List<FlashcardCategory> =
        database.flashcardDao().getAllCategories()

    fun getFlashcardsByCategory(categoryId: Int): List<Flashcard> =
        database.flashcardDao().getFlashcardsByCategoryId(categoryId)
}
