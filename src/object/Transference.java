package sr.pago.sdk.object;

/**
 * Created by Rodolfo on 13/07/2017.
 */

public class Transference extends PixzelleClass {
    private Person source;
    private Person destination;

    public Transference(){
        source = new Person();
        destination = new Person();
    }

    public Person getSource() {
        return source;
    }

    public void setSource(Person source) {
        this.source = source;
    }

    public Person getDestination() {
        return destination;
    }

    public void setDestination(Person destination) {
        this.destination = destination;
    }

    public class Person extends PixzelleClass{
        private String lastName;
        private String surname;
        private String email;
        private String username;
        private boolean active;
        private boolean fullProfile;
        private boolean emailSent;
        private int hoursReversal;

        public String getLastName() {
            return lastName.trim();
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getSurname() {
            return surname.trim();
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public boolean isFullProfile() {
            return fullProfile;
        }

        public void setFullProfile(boolean fullProfile) {
            this.fullProfile = fullProfile;
        }

        public boolean isEmailSent() {
            return emailSent;
        }

        public void setEmailSent(boolean emailSent) {
            this.emailSent = emailSent;
        }

        public int getHoursReversal() {
            return hoursReversal;
        }

        public void setHoursReversal(int hoursReversal) {
            this.hoursReversal = hoursReversal;
        }
    }
}
