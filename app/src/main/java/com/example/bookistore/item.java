package com.example.bookistore;

import java.util.ArrayList;


    public class item {
        String name, description,bookId;
        String price;
        ArrayList <Integer> typeOfFragment;

       /* public item(String mName, String mImageUrl) {
            if (mName.trim().equals("")) {
                mName = "No Name";
            }
            this.mName = mName;
            this.mImageUrl = mImageUrl;
        }*/

       /* public item(String name, String description, String bookId, String price, ArrayList<Integer> typeOfFragment, String mName, String mImageUrl) {

            this.name = name;
            this.description = description;
            this.bookId = bookId;
            this.price = price;
            this.typeOfFragment = typeOfFragment;
            this.mName = mName;
            this.mImageUrl = mImageUrl;
        }*/

        public item(String name, String description, String bookId, String price, ArrayList<Integer> typeOfFragment) {
            this.name = name;
            this.description = description;
            this.bookId = bookId;
            this.price = price;
            this.typeOfFragment = typeOfFragment;
        }

       /* private String mName;
        private String mImageUrl;*/



        public item() {
        }

       /* public item(String trim, Task<Uri> downloadUrl) {
            if (mName.trim().equals("")) {
                mName = "No Name";
            }
            this.mName = mName;
            this.mImageUrl = mImageUrl;
        }*/

      /*  public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmImageUrl() {
            return mImageUrl;
        }

        public void setmImageUrl(String mImageUrl) {
            this.mImageUrl = mImageUrl;
        }*/

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ArrayList <Integer> getTypeOfFragment() {
            return typeOfFragment;
        }

        public void setTypeOfFragment(ArrayList <Integer> typeOfFragment) {
            this.typeOfFragment = typeOfFragment;
        }
    }


