(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('MovieParticipant', MovieParticipant);

    MovieParticipant.$inject = ['$resource', 'DateUtils'];

    function MovieParticipant ($resource, DateUtils) {
        var resourceUrl =  'api/movie-participants/:id';

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
