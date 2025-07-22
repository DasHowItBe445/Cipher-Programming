# Cipher-Programming

CipherProgram – Java Implementation of Classical Ciphers

This Java console-based application implements three classical encryption algorithms:

    Additive Cipher

    Multiplicative Cipher

    Affine Cipher

It provides a simple menu-driven interface to encrypt and decrypt text using user-defined keys, demonstrating how each cipher works. This project is a great educational tool for learning the basics of classical cryptography and modular arithmetic.
Features

    Encrypt and decrypt any alphabetic text using:

        Additive Cipher (Caesar Cipher)

        Multiplicative Cipher (with modular inverse support)

        Affine Cipher (combined additive and multiplicative encryption)

    Validates key constraints (e.g., coprimality with 26 for multiplicative/affine ciphers)

    Clean and modular Java implementation

    Menu-based user interaction via CLI

How It Works

    Uses ASCII manipulation for letter shifting

    Includes a utility method for calculating modular inverses, essential for decryption

    Processes both uppercase and lowercase letters, ignoring non-alphabetic characters

    Demonstrates classical cipher logic using clear switch-case structure
