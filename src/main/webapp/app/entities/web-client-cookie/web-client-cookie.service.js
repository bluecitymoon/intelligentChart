(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('WebClientCookie', WebClientCookie);

    WebClientCookie.$inject = ['$resource', 'DateUtils'];

    function WebClientCookie ($resource, DateUtils) {
        var resourceUrl =  'api/web-client-cookies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.expires = DateUtils.convertLocalDateFromServer(data.expires);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.expires = DateUtils.convertLocalDateToServer(copy.expires);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.expires = DateUtils.convertLocalDateToServer(copy.expires);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
