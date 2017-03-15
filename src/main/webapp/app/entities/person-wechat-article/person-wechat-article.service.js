(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonWechatArticle', PersonWechatArticle);

    PersonWechatArticle.$inject = ['$resource', 'DateUtils'];

    function PersonWechatArticle ($resource, DateUtils) {
        var resourceUrl =  'api/person-wechat-articles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertLocalDateFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            },
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-wechat-articles/person/:id',
                params: { id : '@id'}
            },
            'loadLatestWechatArticleCount' : {
                method: 'GET',
                url: 'api/person-wechat-articles/person/:id/current',
                params: { id : '@id'}
            }
        });
    }
})();
