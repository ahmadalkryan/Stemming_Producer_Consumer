producer-consumer-system/
├── 📄 Main.java           # نقطة بداية البرنامج
├── 📄 Producer.java       # المنتج - يقرأ من الملف
├── 📄 Consumer.java       # المستهلك - يعالج النصوص
├── 📄 Writer.java         # الكاتب - يكتب النتائج
├── 📄 Stemmer.java        # المجذِّر - يحول الكلمات لجذورها
├── 📄 input.txt          # ملف الإدخال (يتم إنشاؤه)
├── 📄 output.txt         # ملف الإخراج (يتم إنشاؤه)
└── 📄 README.md          # هذا الملف

1-
// Reads file line by line
// Adds each line to Queue 1
// Sends EOF signals when finished

2-
// Each consumer takes a line from Queue 1
// Splits line into words
// Stems each word using Stemmer
// Places result in Queue 2

3-
// Takes results from Queue 2
// Writes to output file
// Stops after receiving all EOF signals


1. Reading ← Producer ← input.txt
2. Buffering ← Queue 1 (LinkedBlockingQueue)
3. Processing ← Consumer(s) ← Stemmer
4. Buffering ← Queue 2 (LinkedBlockingQueue)
5. Writing ← Writer → output.txt
