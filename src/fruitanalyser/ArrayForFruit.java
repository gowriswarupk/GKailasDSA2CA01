package fruitanalyser;

public class ArrayForFruit {

    int[] array;
    int imageW, imageH;

    public ArrayForFruit(int imageW, int imageH){
        this.imageH = imageH;
        this.imageW = imageW;
        this.array = new int[imageH*imageW];
    }

    public int calcArrayPosition(int y, int x){
        return (y*imageW) + x;
    }

    public void addFruitToArray(int y, int x){
        int pos = calcArrayPosition(x,y);
        array[pos] = pos;
    }

    public void addNonFruitToArray(int y, int x){
        array[calcArrayPosition(x,y)] = -1;
    }

    public boolean whitePixel(int i){
        return array[i] != -1;
    }

    public boolean atTop(int i){
        return ((i - imageW) < 0 );
    }

    public boolean atBottom(int i){
        return ((i + imageW) > (imageW * imageH) );
    }

    public boolean atRight(int i){
        return (((2*(i+1)) % imageW) == 0 );
    }

    public boolean atLeft(int i){
        return ((i % imageW) == 0 );
    }

    public void unionPixels(int a, int b){
        if ( find(array ,a) < find(array, b)){
            qU(array, a, b);
        }
        else qU(array, b, a);
    }

    public void unionFruitPixels(){
        for ( int i = 0; i<array.length;i++){
            if (whitePixel(i)){
                int top, bottom, right, left;
                top = i-imageW;
                bottom = i + imageW;
                right = i+1;
                left = i-1;
                if(!atTop(i) && whitePixel(top)){
                    unionPixels(i, top);
                }
                if(!atRight(i) && whitePixel(right)){
                    unionPixels(i, right);
                }
                if(!atLeft(i) && whitePixel(left)){
                    unionPixels(i, left);
                }
                if(!atBottom(i) && whitePixel(bottom)){
                    unionPixels(i, bottom);
                }
            }
        }
    }

    public int find(int[] a, int id) {
        return a[id] == id ? id : (a[id] = find(a, a[id]));
    }

    public void qU(int[] a, int p, int q){
        array[find(a,q)] = find (a,p);
    }

}
