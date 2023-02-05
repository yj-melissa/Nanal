//package com.dbd.nanal.tensorflow;
//import org.tensorflow.*;
//import org.tensorflow.op.Ops;
//import org.tensorflow.op.core.Placeholder;
//import org.tensorflow.op.math.Add;
//import org.tensorflow.types.TInt32;
//
//
//public class TensorflowTest {
//
//    public static void main(String[] args) throws Exception {
//        System.out.println("Hello TensorFlow " + TensorFlow.version());
//
////        getDataSize(filePath);
////        System.out.println("[number of row] ==> "+ROW);
////        System.out.println(" / [number of feature] ==> "+FEATURE);
//
//        String filePath = "resources/model";
//
////        Session sess =
//        try(SavedModelBundle b = SavedModelBundle.load(filePath,"serve")){
////            model_from_joblib = joblib.load('./model/iris_model.pkl')
//            // create a session from the Bundle
//            Session sess = b.session();
//            System.out.println("SavedModelBundle: " + b.metaGraphDef());
//
//            // create an input Tensor
////            Tensor x = Tensor.create();
//            Tensor x = sess.runner().fetch("My").run().get(0);
//
//
//
//        }
//
//
//
//
//
//        try (ConcreteFunction dbl = ConcreteFunction.create(TensorflowTest::dbl);
//             TInt32 x = TInt32.scalarOf(10);
//             Tensor dblX = dbl.call(x)) {
//            System.out.println(x.getInt() + " doubled is " + ((TInt32)dblX).getInt());
//        }
//    }
//
//    private static Signature dbl(Ops tf) {
//        Placeholder<TInt32> x = tf.placeholder(TInt32.class);
//        Add<TInt32> dblX = tf.math.add(x, x);
//        return Signature.builder().input("x", x).output("dbl", dblX).build();
//    }
//}