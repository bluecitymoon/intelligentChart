(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonPrize', PersonPrize);

    PersonPrize.$inject = ['$resource', 'DateUtils'];

    function PersonPrize ($resource, DateUtils) {
        var resourceUrl =  'api/person-prizes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.prizeDate = DateUtils.convertLocalDateFromServer(data.prizeDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.prizeDate = DateUtils.convertLocalDateToServer(copy.prizeDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.prizeDate = DateUtils.convertLocalDateToServer(copy.prizeDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
