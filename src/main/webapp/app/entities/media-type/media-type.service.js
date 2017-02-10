(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('MediaType', MediaType);

    MediaType.$inject = ['$resource'];

    function MediaType ($resource) {
        var resourceUrl =  'api/media-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
