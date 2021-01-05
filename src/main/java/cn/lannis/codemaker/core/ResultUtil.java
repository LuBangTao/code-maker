package cn.lannis.codemaker.core;

/**
 * <p>描述：返回对象生成工具</p>
 * <p>公司：Lannis©2021 All Rights Reserved</p>
 * <p>作者：鲁帮涛</p>
 * <p>日期：2020-12-01 11:44</p>
 * <p>版权：RivaMed-2021</p>
 */
public class ResultUtil {
    ResultUtil(){}
    public static <T> Result<T>  fail(){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMsg(ResultCode.FAIL.getMessage());
        return result;
    }
    public static <T> Result<T> fail(String failDetail){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMsg(failDetail);
        return result;
    }

    public static <T> Result<T> success(){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }
}
