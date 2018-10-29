package com.amoharib.booketlist.app.data.remote;

import android.support.annotation.NonNull;

import com.amoharib.booketlist.app.data.DataRepository;
import com.amoharib.booketlist.app.data.local.Book;
import com.amoharib.booketlist.app.data.remote.endpoint.BookAPI;
import com.amoharib.booketlist.app.data.remote.model.BookDescription;
import com.amoharib.booketlist.app.data.remote.model.Results;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RemoteRepository implements DataRepository.Remote {
    private BookAPI bookAPI;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public RemoteRepository(BookAPI bookAPI) {
        this.bookAPI = bookAPI;
    }

    @Override
    public Observable<Results> getResults(String query) {
        return bookAPI.getBooks(query);
    }

    @Override
    public Observable<BookDescription> getBookDescription(String bookId) {
        return bookAPI.getBookDescription(bookId);
    }

    //region Authentication

    @Override
    public void login(String email, String password, Action success, Consumer<String> error) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(result -> {
            try {

                if (result.isSuccessful()) {
                    success.run();
                } else {
                    error.accept(Objects.requireNonNull(result.getException()).getLocalizedMessage());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void register(String email, String password, Action success, Consumer<String> error) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(result -> {
            try {

                if (result.isSuccessful()) {
                    success.run();
                } else {
                    error.accept(Objects.requireNonNull(result.getException()).getLocalizedMessage());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void logout() {
        auth.signOut();
    }

    @Override
    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    //endregion

    //region Database

    @Override
    public void getAllBooksRemotely(Consumer<List<Book>> books) {

        ref.child("books").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    List<Book> newBooks = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Book book = child.getValue(Book.class);
                        newBooks.add(book);
                    }
                    books.accept(newBooks);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                ref.removeEventListener(this);
            }
        });

    }

    @Override
    public void saveBookRemotely(Book book, Action success, Consumer<String> error) {
        ref.child("books").child(auth.getUid()).child(book.getId()).setValue(book).addOnCompleteListener(
                task -> {

                    try {
                        if (task.isSuccessful()) {
                            success.run();
                        } else {
                            error.accept(Objects.requireNonNull(task.getException()).getLocalizedMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
    }

    @Override
    public void deleteBookRemotely(String bookId, Action success, Consumer<String> error) {
        ref.child("books").child(auth.getUid()).child(bookId).removeValue().addOnCompleteListener(
                task -> {

                    try {
                        if (task.isSuccessful()) {
                            success.run();
                        } else {
                            error.accept(Objects.requireNonNull(task.getException()).getLocalizedMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
    }

    @Override
    public void updateBooks(List<Book> books) {
        Map<String, Object> booksMap = new HashMap<>();
        for (Book book : books) {
            booksMap.put(book.getId(), book);
        }
        ref.child("books").child(auth.getUid()).updateChildren(booksMap);
    }

    //endregion
}
