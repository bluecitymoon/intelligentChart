(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('RobotMovieSubjectFailPage', RobotMovieSubjectFailPage);

    RobotMovieSubjectFailPage.$inject = ['$resource', 'DateUtils'];

    function RobotMovieSubjectFailPage ($resource, DateUtils) {
        var resourceUrl =  'api/robot-movie-subject-fail-pages/:id';

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
