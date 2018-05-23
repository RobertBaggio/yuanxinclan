package com.yuanxin.clan.mvp.payment;


/**
 * date 2015/9/15.
 */
public class PaymentKey {

//    static {
//        System.loadLibrary("Key");
//    }
//
//    public native String getAliPartnerId();
//
//    public native String getAliSellerId();
//
//    public native String getAliPrivateKey();
//
//    public native String getAliPublicKey();
//
//    public native String getWXAPP_Id();
//
//    public native String getWXMCH_ID();
//
//    public native String getWXAPI_Key();
//
//    public static String getAPPSignaturesSha1() {
//        String signString = null;
//        try {
//            PackageInfo packageInfo = UIUtils.getContext().getPackageManager().getPackageInfo(UIUtils.getContext().getPackageName(), PackageManager.GET_SIGNATURES);
//            android.content.pm.Signature[] signatures = packageInfo.signatures;
//            byte[] cert = signatures[0].toByteArray();
//            StringBuilder shaBuilder = new StringBuilder();
//            MessageDigest md = MessageDigest.getInstance("SHA1");
//            md.update(cert);
//            for (byte b : md.digest()) {
//                shaBuilder.append(Integer.toString(b & 0xff, 16));
//            }
//            return shaBuilder.toString().toUpperCase();
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return signString;
//    }
}

