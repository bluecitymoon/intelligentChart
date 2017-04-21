(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('MovieSuccessLog', MovieSuccessLog);

    MovieSuccessLog.$inject = ['$resource', 'DateUtils'];

    function MovieSuccessLog ($resource, DateUtils) {
        var resourceUrl =  'api/movie-success-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
