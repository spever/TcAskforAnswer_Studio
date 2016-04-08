package com.tuanche.askforanswer.source.bean;

import java.util.HashMap;
import java.util.List;

public class QuestionDatialBean {

		/**
		 * 
		 */
		private int Count;
		private String advert;
		private int currentPage;
	
		private int total_count;//总数（包含追问和回答）
		private int answer_count;//回答数
		private int run_count;

		private DetialResultDetialBean detail;
		private int pageNum;
		
		
		public int getCount() {
			return Count;
		}
		public void setCount(int count) {
			Count = count;
		}
		public int getCurrentPage() {
			return currentPage;
		}
		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}
		public int getPageNum() {
			return pageNum;
		}
		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}
		public String getAdvert() {
			return advert;
		}
		public void setAdvert(String advert) {
			this.advert = advert;
		}
		public DetialResultDetialBean getDetail() {
			return detail;
		}
		public void setDetail(DetialResultDetialBean detail) {
			this.detail = detail;
		}
		
		public int getTotal_count() {
			return total_count;
		}
		public void setTotal_count(int total_count) {
			this.total_count = total_count;
		}

		public int getAnswer_count() {
			return answer_count;
		}
		public void setAnswer_count(int answer_count) {
			this.answer_count = answer_count;
		}

		public int getRun_count() {
			return run_count;
		}
		public void setRun_count(int run_count) {
			this.run_count = run_count;
		}

		public class DetialResultDetialBean{
			/**
			 * 
			 */
		//	private static final long serialVersionUID = 1L;
			private List<ReplyItemDetail> answer;
			private HashMap<String, String> question;
			
			public HashMap<String, String> getQuestion() {
				return question;
			}
			public void setQuestion(HashMap<String, String> question) {
				this.question = question;
			}
			public List<ReplyItemDetail> getAnswer() {
				return answer;
			}
			public void setAnswer(List<ReplyItemDetail> answer) {
				this.answer = answer;
			}
			public class ReplyItemDetail{
				private AcceptDate acceptDate;
				private int acceptStatus;
				private String answerContent;
				
				private AnswerDate answerDate;
				private String answerDateStr;
				private long answerUserId;
				
				private int askId;
				private String icon;
				private int id;
				
				private int type;
				private int userId;
				private String userName;
				
				public AcceptDate getAcceptDate() {
					return acceptDate;
				}
				public void setAcceptDate(AcceptDate acceptDate) {
					this.acceptDate = acceptDate;
				}
				public int getAcceptStatus() {
					return acceptStatus;
				}
				public void setAcceptStatus(int acceptStatus) {
					this.acceptStatus = acceptStatus;
				}
				public String getAnswerContent() {
					return answerContent;
				}
				public void setAnswerContent(String answerContent) {
					this.answerContent = answerContent;
				}
				public AnswerDate getAnswerDate() {
					return answerDate;
				}
				public void setAnswerDate(AnswerDate answerDate) {
					this.answerDate = answerDate;
				}
				public String getAnswerDateStr() {
					return answerDateStr;
				}
				public void setAnswerDateStr(String answerDateStr) {
					this.answerDateStr = answerDateStr;
				}
				public long getAnswerUserId() {
					return answerUserId;
				}
				public void setAnswerUserId(long answerUserId) {
					this.answerUserId = answerUserId;
				}
				public int getAskId() {
					return askId;
				}
				public void setAskId(int askId) {
					this.askId = askId;
				}
				public String getIcon() {
					return icon;
				}
				public void setIcon(String icon) {
					this.icon = icon;
				}
				public int getId() {
					return id;
				}
				public void setId(int id) {
					this.id = id;
				}
				public int getType() {
					return type;
				}
				public void setType(int type) {
					this.type = type;
				}
				public int getUserId() {
					return userId;
				}
				public void setUserId(int userId) {
					this.userId = userId;
				}
				public String getUserName() {
					return userName;
				}
				public void setUserName(String userName) {
					this.userName = userName;
				}
				public class AcceptDate{
					private int date;
					private int day;
					private int hours;
					
					private int minutes;
					private int month;
					private int seconds;
					
					private long time;
					private long timezoneOffset;
					private int year;
					public int getDate() {
						return date;
					}
					public void setDate(int date) {
						this.date = date;
					}
					public int getDay() {
						return day;
					}
					public void setDay(int day) {
						this.day = day;
					}
					public int getHours() {
						return hours;
					}
					public void setHours(int hours) {
						this.hours = hours;
					}
					public int getMinutes() {
						return minutes;
					}
					public void setMinutes(int minutes) {
						this.minutes = minutes;
					}
					public int getMonth() {
						return month;
					}
					public void setMonth(int month) {
						this.month = month;
					}
					public int getSeconds() {
						return seconds;
					}
					public void setSeconds(int seconds) {
						this.seconds = seconds;
					}
					public long getTime() {
						return time;
					}
					public void setTime(long time) {
						this.time = time;
					}
					public long getTimezoneOffset() {
						return timezoneOffset;
					}
					public void setTimezoneOffset(long timezoneOffset) {
						this.timezoneOffset = timezoneOffset;
					}
					public int getYear() {
						return year;
					}
					public void setYear(int year) {
						this.year = year;
					}
					
				}
				public class AnswerDate{
					private int date;
					private int day;
					private int hours;
					
					private int minutes;
					private int month;
					private int seconds;
					
					private long time;
					private int timezoneOffset;
					private int year;
					public int getDate() {
						return date;
					}
					public void setDate(int date) {
						this.date = date;
					}
					public int getDay() {
						return day;
					}
					public void setDay(int day) {
						this.day = day;
					}
					public int getHours() {
						return hours;
					}
					public void setHours(int hours) {
						this.hours = hours;
					}
					public int getMinutes() {
						return minutes;
					}
					public void setMinutes(int minutes) {
						this.minutes = minutes;
					}
					public int getMonth() {
						return month;
					}
					public void setMonth(int month) {
						this.month = month;
					}
					public int getSeconds() {
						return seconds;
					}
					public void setSeconds(int seconds) {
						this.seconds = seconds;
					}
					public long getTime() {
						return time;
					}
					public void setTime(long time) {
						this.time = time;
					}
					public int getTimezoneOffset() {
						return timezoneOffset;
					}
					public void setTimezoneOffset(int timezoneOffset) {
						this.timezoneOffset = timezoneOffset;
					}
					public int getYear() {
						return year;
					}
					public void setYear(int year) {
						this.year = year;
					}
				}
			}
			
	}

}
