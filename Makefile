# Variables
SRC_DIR = main
CLASSES_DIR = bin
MAIN_CLASS = main.Main

# Default target to compile and run the Java program
all: compile run

# Compile the Java program
compile:
	@mkdir -p $(CLASSES_DIR)
	javac -d $(CLASSES_DIR) $(SRC_DIR)/Main.java

# Run the compiled Java program
run:
	java -cp $(CLASSES_DIR) $(MAIN_CLASS)

# Clean up compiled class files
clean:
	rm -rf $(CLASSES_DIR)
