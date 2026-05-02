package com.assignment.library.controller;

import com.assignment.library.model.Book;
import com.assignment.library.service.LibraryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", libraryService.getAllBooksWithAuthor());
        return "book-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "book-form";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book, @RequestParam("authorId") Long authorId, RedirectAttributes redirectAttributes) {
        try {
            libraryService.saveBook(book, authorId);
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Book with this ISBN already exists.");
            return "redirect:/books/add";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while saving the book.");
            return "redirect:/books/add";
        }
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Book book = libraryService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "book-update";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("book") Book book, @RequestParam("authorId") Long authorId, RedirectAttributes redirectAttributes) {
        try {
            libraryService.updateBook(id, book, authorId);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Database constraint violation (e.g. duplicate ISBN).");
            return "redirect:/books/edit/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the book.");
            return "redirect:/books/edit/" + id;
        }
        return "redirect:/books";
    }
}
