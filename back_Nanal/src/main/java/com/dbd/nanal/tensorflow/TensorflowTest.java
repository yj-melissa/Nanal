package com.dbd.nanal.tensorflow;
import org.tensorflow.*;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Placeholder;
import org.tensorflow.op.math.Add;
import org.tensorflow.types.TInt32;


public class TensorflowTest {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello TensorFlow " + TensorFlow.version());

//        getDataSize(filePath);
//        System.out.println("[number of row] ==> "+ROW);
//        System.out.println(" / [number of feature] ==> "+FEATURE);

//        SavedModelBundle model = SavedModelBundle.loader("resources/model").load();
//        SavedModelBundle model = SavedModelBundle.loader("C:\\ssafy\\S08P12D110\\back_Nanal\\src\\main\\resources\\model").load();
//        SavedModelBundle model = SavedModelBundle.loader("model").load();
        SavedModelBundle model = SavedModelBundle.loader("/temp").load();
        System.out.println("SavedModelBundle: " + model.metaGraphDef());

//        Session sess =
        // load the model bundle
//        try(SavedModelBundle b = SavedModelBundle.load(filePath)){
////            model_from_joblib = joblib.load('./model/iris_model.pkl')
//            // create a session from the Bundle
//            Session sess = b.session();
//
//            // create an input Tensor
////            Tensor x = Tensor.create();
//
//
//        }





        try (ConcreteFunction dbl = ConcreteFunction.create(TensorflowTest::dbl);
             TInt32 x = TInt32.scalarOf(10);
             Tensor dblX = dbl.call(x)) {
            System.out.println(x.getInt() + " doubled is " + ((TInt32)dblX).getInt());
        }
    }

    private static Signature dbl(Ops tf) {
        Placeholder<TInt32> x = tf.placeholder(TInt32.class);
        Add<TInt32> dblX = tf.math.add(x, x);
        return Signature.builder().input("x", x).output("dbl", dblX).build();
    }
}