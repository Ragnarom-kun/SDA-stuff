public class damn {
    public boolean check(BinaryNode a, BinaryNode b) {
        if (a != null && b != null) {
            return true;
        } else if (a == null & b != null) {
            return false;
        } else if (a != null & b == null) {
            return false;
        } else {
            if (a==b) {
                return true;
            }
        }
        return false;
    }
}
